package com.barabasizsolt.mova.domain.usecase.helper.people

import com.barabasizsolt.mova.people.api.PeopleService
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetPopularPeopleFlowUseCase(private val peopleService: PeopleService) {

    operator fun invoke() = peopleService.popularPeople.filterNotNull().map { it.distinctBy { movie -> movie.id } }
}