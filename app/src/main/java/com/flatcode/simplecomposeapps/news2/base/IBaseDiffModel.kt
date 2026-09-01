package com.flatcode.simplecomposeapps.news2.base

interface IBaseDiffModel<T> {
    val id: T
    override fun equals(other: Any?): Boolean
}