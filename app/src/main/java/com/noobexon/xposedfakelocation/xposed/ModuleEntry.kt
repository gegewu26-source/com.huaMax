package com.noobexon.xposedfakelocation.xposed

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.noobexon.xposedfakelocation.data.REMOTE_PREFS_GROUP
import com.noobexon.xposedfakelocation.xposed.hooks.LocationApiHooks
import com.noobexon.xposedfakelocation.xposed.utils.LocationUtil
import com.noobexon.xposedfakelocation.xposed.utils.PreferencesUtil
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageReadyParam
import io.github.libxposed.api.XposedModuleInterface.SystemServerStartingParam

class ModuleEntry : XposedModule() {
    companion object {
        const val TAG = "[ModuleEntry]"
    }

    private var locationApiHooks: LocationApiHooks? = null
//    private var systemServicesHooks: SystemServicesHooks? = null
//    private var phoneServicesHooks: PhoneServicesHooks? = null

    override fun onModuleLoaded(param: ModuleLoadedParam) {
        log(Log.INFO, TAG, "onModuleLoaded: ${param.processName}")
        LocationUtil.logger = { priority, tag, message -> log(priority, tag, message) }
        PreferencesUtil.logger = { priority, tag, message -> log(priority, tag, message) }
    }

    override fun onPackageLoaded(param: PackageLoadedParam) {
        log(Log.INFO, TAG, "onPackageLoaded: ${param.packageName}")
        log(Log.INFO, TAG, "\tdefault classloder: ${param.defaultClassLoader}")
    }

    override fun onPackageReady(param: PackageReadyParam) {
        log(Log.INFO, TAG, "onPackageReady: ${param.packageName}")
        log(Log.INFO, TAG, "\tapp classloder: ${param.classLoader}")
        log(Log.INFO, TAG, "\tmodule apk path: ${moduleApplicationInfo.sourceDir}")

        // Run per-package setup only once.
        if (!param.isFirstPackage) return

        PreferencesUtil.init(getRemotePreferences(REMOTE_PREFS_GROUP))

        // If not playing or null, do not proceed with hooking
        if (PreferencesUtil.getIsPlaying() != true) return

        initHookingLogic(param)


        // TODO: Integrate the commented code by splitting to cases of system or not system (after i integrate new xposed api)
//        val useInAppTargetApps = PreferencesUtil.getUseInAppTargetApps()
//        when (lpparam.packageName) {
//            "android" -> {
//                if (useInAppTargetApps) {
//                    XposedBridge.log("$tag Mode=IN_APP_TARGET_LIST | Installing system-server location hooks (android).")
//                    systemServicesHooks = SystemServicesHooks(lpparam).also { it.initHooks() }
//                } else {
//                    XposedBridge.log("$tag Mode=LSPOSED_SCOPE_ONLY | Skipping system-server hooks (android). Selection is driven solely by LSPosed scope.")
//                }
//                return
//            }
//            "com.android.phone" -> {
//                if (useInAppTargetApps) {
//                    XposedBridge.log("$tag Mode=IN_APP_TARGET_LIST | Installing phone-process location side-channel hooks (com.android.phone).")
//                    phoneServicesHooks = PhoneServicesHooks(lpparam).also { it.initHooks() }
//                } else {
//                    XposedBridge.log("$tag Mode=LSPOSED_SCOPE_ONLY | Skipping phone-process hooks (com.android.phone).")
//                }
//                return
//            }
//            MANAGER_APP_PACKAGE_NAME -> return
//            else -> {
//                XposedBridge.log("$tag Mode=${if (useInAppTargetApps) "IN_APP_TARGET_LIST" else "LSPOSED_SCOPE_ONLY"} | Installing in-process location hooks for ${lpparam.packageName}")
//                initHookingLogic(lpparam)
//            }
//        }
    }

    override fun onSystemServerStarting(param: SystemServerStartingParam) {
        log(Log.INFO, TAG, "onSystemServerStarting:\n\t${param.classLoader}")
    }

    private fun initHookingLogic(param: PackageReadyParam) {
        val clazz = Class.forName("android.app.Instrumentation", false, param.classLoader)
        val method = clazz.getDeclaredMethod("callApplicationOnCreate", Application::class.java)

        hook(method).intercept { chain ->
            val result = chain.proceed()

            try {
                val context = (chain.getArg(0) as Application).applicationContext
                log(Log.INFO, TAG, "Target App's context has been acquired (${param.packageName}).")
                if (PreferencesUtil.getHideFakeLocationToast() != true) {
                    Toast.makeText(context, "Fake Location Is Active!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                log(Log.ERROR, TAG, "Toast/context failed - ${e.message}")
            }

            locationApiHooks = LocationApiHooks(this, param.classLoader).also { it.initHooks() }
            result
        }
    }
}