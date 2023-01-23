package com.barabasizsolt.domain.usecase.helper.people

import com.barabasizsolt.domain.util.result.wrapToResult
import com.barabasizsolt.pagination.api.RefreshType
import com.barabasizsolt.people.api.PeopleService

class GetPopularPeopleUseCase(private val peopleService: PeopleService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        peopleService.getPopularPeople(refreshType = refreshType).distinctBy { it.id }
    }
}