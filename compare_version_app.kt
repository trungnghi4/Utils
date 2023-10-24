// Method to compare two versions.
// Returns 1 if v2 is smaller, -1 if v1 is smaller, 0 if equal
private fun versionCompare(v1: String, v2: String): Int {
    val version1 = v1.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val version2 = v2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val length = version1.size.coerceAtLeast(version2.size)
    for (i in 0 until length) {
        val num1 = if (i < version1.size) version1[i].toInt() else 0
        val num2 = if (i < version2.size) version2[i].toInt() else 0
        if (num1 > num2) return 1
        if (num2 > num1) return -1
    }
    return 0
}

fun versionCompare2(v1: String, v2: String): Int {
    if (v1.isEmpty() || v2.isEmpty()) return 0
    val version1 = v1.split(".").map { it.toInt() }
    val version2 = v2.split(".").map { it.toInt() }
    val length = maxOf(version1.size, version2.size)
    for (i in 0 until length) {
        val num1 = if (i < version1.size) version1[i] else 0
        val num2 = if (i < version2.size) version2[i] else 0
        if (num1 > num2) return 1
        if (num2 > num1) return -1
    }
    return 0
}

