package com.github.skiiyis.plugin

import com.quinn.hunter.transform.asm.BaseWeaver
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class CheckLogByteCodeWeaver(private val extension: VariantExtension) : BaseWeaver() {

    override fun isWeavableClass(fullQualifiedClassName: String?): Boolean {
        val include = extension.include.let {
            if (it == null) {
                true
            } else {
                it.firstOrNull { fullQualifiedClassName?.contains(it) ?: false } != null
            }
        }
        val exclude = extension.exclude?.firstOrNull {
            fullQualifiedClassName?.contains(it) ?: false
        } != null
        return include && !exclude && super.isWeavableClass(fullQualifiedClassName)
    }

    override fun wrapClassWriter(classWriter: ClassWriter?): ClassVisitor {
        return CheckLogClassVisitor(classWriter, extension)
    }
}

class CheckLogClassVisitor(classWriter: ClassWriter?, private val extension: VariantExtension) :
    ClassVisitor(Opcodes.ASM5, classWriter) {

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor? {
        val mv = cv.visitMethod(access, name, desc, signature, exceptions)
        return if (mv == null) null else CheckLogClassMethodVisitor(
            mv,
            extension
        )
    }
}

class CheckLogClassMethodVisitor(
    mv: MethodVisitor?,
    private val extension: VariantExtension
) : MethodVisitor(Opcodes.ASM5, mv) {

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        desc: String?,
        itf: Boolean
    ) {
        if ("android/util/Log" != owner || extension.methods?.contains(name) != true) {
            super.visitMethodInsn(opcode, owner, name, desc, itf)
            return
        }
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            extension.replace,
            name,
            desc,
            false
        )
    }
}