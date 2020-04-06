package io.autorune.injector.type

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.autorune.injector.Injector
import io.autorune.injector.PackageConstants
import io.autorune.injector.transform.*
import org.objectweb.asm.tree.ClassNode

class ClassInjector(private val classJson: JsonObject, private val classNode: ClassNode) : Injector()
{

    override fun runInjection() {

        val pkg = classJson.get("package").asString.replace(".", "/")
        val refName = classJson.get("ref_name").asString

        val apiPkg = PackageConstants.osrsApiPkg

        classNode.interfaces.add("$apiPkg$pkg/$refName")

        checkForTransform(refName)

    }

    private fun checkForTransform(refName: String) {

        if (refName == "MouseListener" || refName == "MouseWheelListener")
            DeviceTransform.injectTransform(classNode, "mouse")

        if (refName == "KeyboardListener")
            DeviceTransform.injectTransform(classNode, "key")

        if (refName == "GameShell")
           GameShellTransform.injectTransform(classNode, classJson)

        if (refName == "RasterProvider")
            RasterProviderTransform.injectTransform(classNode, classJson)


    }

}