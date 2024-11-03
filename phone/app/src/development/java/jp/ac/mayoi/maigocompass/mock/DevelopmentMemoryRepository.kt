package jp.ac.mayoi.maigocompass.mock

import jp.ac.mayoi.phone.model.UserMemory
import jp.ac.mayoi.repository.interfaces.MemoryRepository

class DevelopmentMemoryRepository : MemoryRepository {
    override suspend fun getUserMemory(): UserMemory {
        TODO("Not yet implemented")
    }
}