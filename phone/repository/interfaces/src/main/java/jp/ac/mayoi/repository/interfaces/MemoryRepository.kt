package jp.ac.mayoi.repository.interfaces

import jp.ac.mayoi.phone.model.UserMemory

interface MemoryRepository {
    suspend fun getUserMemory(): UserMemory
}