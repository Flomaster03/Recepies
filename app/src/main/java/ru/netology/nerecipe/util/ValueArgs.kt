package ru.netology.nmedia.util

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object StringArgs: ReadWriteProperty<Bundle, String?> {

    override fun getValue(thisRef: Bundle, property: KProperty<*>): String? {
        return thisRef.getString(property.name)
    }

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
        thisRef.putString(property.name, value)
    }
}

object LongArgs: ReadWriteProperty<Bundle, Long?> {

    override fun getValue(thisRef: Bundle, property: KProperty<*>): Long {
        return thisRef.getLong(property.name)
    }

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Long?) {
        if (value != null) {
            thisRef.putLong(property.name, value)
        }
    }
}