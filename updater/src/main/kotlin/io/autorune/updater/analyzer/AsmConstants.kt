package io.autorune.updater.analyzer

open class AsmConstants {

    val descByte = "B"
    val descByteArr = "[$descByte"
    val descByteArrArr = "[[$descByte"

    val descShort = "S"
    val descShortArr = "[$descShort"
    val descShortArrArr = "[[$descShort"
    val descShortArrArrArr = "[[[$descShort"

    val descInt = "I"
    val descIntArr = "[$descInt"
    val descIntArrArr = "[[$descInt"
    val descIntArrArrArr = "[[[$descInt"

    val descFloat = "F"
    val descFloatArr = "[$descFloat"
    val descFloatArrArr = "[[$descFloat"

    val descLong = "J"
    val descLongArr = "[$descLong"
    val descLongArrArr = "[[$descLong"

    val descDouble = "D"
    val descDoubleArr = "[$descDouble"
    val descDoubleArrArr = "[[$descDouble"

    val descBool = "Z"
    val descBool1D = "[$descBool"
    val descBool2D = "[[$descBool"
    val descBool3D = "[[$descBool"
    val descBool4D = "[[[[$descBool"

    val descChar = "C"
    val descCharArr = "[$descChar"
    val descCharArrArr = "[[$descChar"

    val descObject = "Ljava/lang/Object;"
    val descObjectArr = "[$descObject"
    val descObjectArrArr = "[[$descObject"

    val descString = "Ljava/lang/String;"
    val descStringArr = "[$descString"
    val descStringArrArr = "[[$descString"

    val NOP = 0 // visitInsn
    val ACONST_NULL = 1 // -
    val ICONST_M1 = 2 // -
    val ICONST_0 = 3 // -
    val ICONST_1 = 4 // -
    val ICONST_2 = 5 // -
    val ICONST_3 = 6 // -
    val ICONST_4 = 7 // -
    val ICONST_5 = 8 // -
    val LCONST_0 = 9 // -
    val LCONST_1 = 10 // -
    val FCONST_0 = 11 // -
    val FCONST_1 = 12 // -
    val FCONST_2 = 13 // -
    val DCONST_0 = 14 // -
    val DCONST_1 = 15 // -
    val BIPUSH = 16 // visitIntInsn
    val SIPUSH = 17 // -
    val LDC = 18 // visitLdcInsn
    val ILOAD = 21 // visitVarInsn
    val LLOAD = 22 // -
    val FLOAD = 23 // -
    val DLOAD = 24 // -
    val ALOAD = 25 // -
    val IALOAD = 46 // visitInsn
    val LALOAD = 47 // -
    val FALOAD = 48 // -
    val DALOAD = 49 // -
    val AALOAD = 50 // -
    val BALOAD = 51 // -
    val CALOAD = 52 // -
    val SALOAD = 53 // -
    val ISTORE = 54 // visitVarInsn
    val LSTORE = 55 // -
    val FSTORE = 56 // -
    val DSTORE = 57 // -
    val ASTORE = 58 // -
    val IASTORE = 79 // visitInsn
    val LASTORE = 80 // -
    val FASTORE = 81 // -
    val DASTORE = 82 // -
    val AASTORE = 83 // -
    val BASTORE = 84 // -
    val CASTORE = 85 // -
    val SASTORE = 86 // -
    val POP = 87 // -
    val POP2 = 88 // -
    val DUP = 89 // -
    val DUP_X1 = 90 // -
    val DUP_X2 = 91 // -
    val DUP2 = 92 // -
    val DUP2_X1 = 93 // -
    val DUP2_X2 = 94 // -
    val SWAP = 95 // -
    val IADD = 96 // -
    val LADD = 97 // -
    val FADD = 98 // -
    val DADD = 99 // -
    val ISUB = 100 // -
    val LSUB = 101 // -
    val FSUB = 102 // -
    val DSUB = 103 // -
    val IMUL = 104 // -
    val LMUL = 105 // -
    val FMUL = 106 // -
    val DMUL = 107 // -
    val IDIV = 108 // -
    val LDIV = 109 // -
    val FDIV = 110 // -
    val DDIV = 111 // -
    val IREM = 112 // -
    val LREM = 113 // -
    val FREM = 114 // -
    val DREM = 115 // -
    val INEG = 116 // -
    val LNEG = 117 // -
    val FNEG = 118 // -
    val DNEG = 119 // -
    val ISHL = 120 // -
    val LSHL = 121 // -
    val ISHR = 122 // -
    val LSHR = 123 // -
    val IUSHR = 124 // -
    val LUSHR = 125 // -
    val IAND = 126 // -
    val LAND = 127 // -
    val IOR = 128 // -
    val LOR = 129 // -
    val IXOR = 130 // -
    val LXOR = 131 // -
    val IINC = 132 // visitIincInsn
    val I2L = 133 // visitInsn
    val I2F = 134 // -
    val I2D = 135 // -
    val L2I = 136 // -
    val L2F = 137 // -
    val L2D = 138 // -
    val F2I = 139 // -
    val F2L = 140 // -
    val F2D = 141 // -
    val D2I = 142 // -
    val D2L = 143 // -
    val D2F = 144 // -
    val I2B = 145 // -
    val I2C = 146 // -
    val I2S = 147 // -
    val LCMP = 148 // -
    val FCMPL = 149 // -
    val FCMPG = 150 // -
    val DCMPL = 151 // -
    val DCMPG = 152 // -
    val IFEQ = 153 // visitJumpInsn
    val IFNE = 154 // -
    val IFLT = 155 // -
    val IFGE = 156 // -
    val IFGT = 157 // -
    val IFLE = 158 // -
    val IF_ICMPEQ = 159 // -
    val IF_ICMPNE = 160 // -
    val IF_ICMPLT = 161 // -
    val IF_ICMPGE = 162 // -
    val IF_ICMPGT = 163 // -
    val IF_ICMPLE = 164 // -
    val IF_ACMPEQ = 165 // -
    val IF_ACMPNE = 166 // -
    val GOTO = 167 // -
    val JSR = 168 // -
    val RET = 169 // visitVarInsn
    val TABLESWITCH = 170 // visiTableSwitchInsn
    val LOOKUPSWITCH = 171 // visitLookupSwitch
    val IRETURN = 172 // visitInsn
    val LRETURN = 173 // -
    val FRETURN = 174 // -
    val DRETURN = 175 // -
    val ARETURN = 176 // -
    val RETURN = 177 // -
    val GETSTATIC = 178 // visitFieldInsn
    val PUTSTATIC = 179 // -
    val GETFIELD = 180 // -
    val PUTFIELD = 181 // -
    val INVOKEVIRTUAL = 182 // visitMethodInsn
    val INVOKESPECIAL = 183 // -
    val INVOKESTATIC = 184 // -
    val INVOKEINTERFACE = 185 // -
    val INVOKEDYNAMIC = 186 // visitInvokeDynamicInsn
    val NEW = 187 // visitTypeInsn
    val NEWARRAY = 188 // visitIntInsn
    val ANEWARRAY = 189 // visitTypeInsn
    val ARRAYLENGTH = 190 // visitInsn
    val ATHROW = 191 // -
    val CHECKCAST = 192 // visitTypeInsn
    val INSTANCEOF = 193 // -
    val MONITORENTER = 194 // visitInsn
    val MONITOREXIT = 195 // -
    val MULTIANEWARRAY = 197 // visitMultiANewArrayInsn
    val IFNULL = 198 // visitJumpInsn
    val IFNONNULL = 199 // -
    
}