package com.barabasizsolt.domain.usecase.helper.people

import com.barabasizsolt.domain.util.wrapToResult
import com.barabasizsolt.people.api.PeopleService

class GetPopularPeopleUseCase(private val peopleService: PeopleService) {

    suspend operator fun invoke(page: Int = 1) = wrapToResult { peopleService.getPopularPeople(page = page) }
}