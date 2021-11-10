package com.ismin.csproject

class FountainShelf {

    private val storage = HashMap<String, Fountain>()

    fun addFountain(ftn: Fountain) {
        storage[ftn.id] = ftn
    }

    fun getFountain(id: String): Fountain? {
        return storage[id]
    }

    fun getAllFountains(): ArrayList<Fountain> {
        return  ArrayList(storage.values.sortedBy { it.id })
    }

    fun getTotalNumberOfFountains(): Int {
        return storage.size
    }
}