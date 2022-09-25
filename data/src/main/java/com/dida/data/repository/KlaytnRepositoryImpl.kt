package com.dida.data.repository

import com.dida.data.api.KlaytnAPIService
import com.dida.data.api.handleApi
import com.dida.data.mapper.toDomain
import com.dida.domain.NetworkResult
import com.dida.domain.model.klaytn.Asset
import com.dida.domain.repository.KlaytnRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named


class KlaytnRepositoryImpl @Inject constructor(
    @Named("Klaytn") private val klaytnAPIService: KlaytnAPIService
) : KlaytnRepository {

    override suspend fun uploadAsset(file: MultipartBody.Part): NetworkResult<Flow<Asset>> {
        return handleApi {
            flow {
                emit(klaytnAPIService.uploadAsset(file).toDomain())
            }.flowOn(Dispatchers.IO)
        }
    }
}

