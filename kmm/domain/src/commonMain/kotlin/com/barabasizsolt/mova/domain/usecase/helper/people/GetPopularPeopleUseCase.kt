package com.barabasizsolt.mova.domain.usecase.helper.people

import com.barabasizsolt.mova.domain.util.wrapToResult
import com.barabasizsolt.mova.pager.RefreshType
import com.barabasizsolt.mova.people.api.PeopleService

class GetPopularPeopleUseCase(private val peopleService: PeopleService) {

    suspend operator fun invoke(refreshType: RefreshType) = wrapToResult {
        peopleService.getPopularPeople(refreshType = refreshType).distinctBy { it.id }
    }
}