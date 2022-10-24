package com.barabasizsolt.domain.usecase.helper.people

import com.barabasizsolt.people.api.PeopleService
import kotlinx.coroutines.flow.filterNotNull

class GetPopularPeopleFlowUseCase(private val peopleService: PeopleService) {

    operator fun invoke() = peopleService.popularPeople.filterNotNull()
}