package com.barabasizsolt.domain.usecase.helper.people

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.people.api.PeopleService
import com.barabasizsolt.util.RefreshType

class GetPopularPeopleUseCase(private val peopleService: PeopleService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        peopleService.getPopularPeople(refreshType = refreshType).distinctBy { it.id }
    }
}