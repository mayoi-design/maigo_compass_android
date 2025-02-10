package jp.ac.mayoi.phone.model.interfaces

interface StringEnum<T : Enum<T>> {
    val remoteToken: String
    val localToken: String
}

abstract class StringEnumAdapter<T : Enum<T>> {
    abstract val default: T
    abstract fun fromRemote(remoteToken: String): T
}
