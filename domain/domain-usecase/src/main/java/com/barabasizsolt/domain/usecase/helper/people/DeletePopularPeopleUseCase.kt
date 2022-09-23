package com.barabasizsolt.domain.usecase.helper.people

import com.barabasizsolt.people.api.PeopleService

class DeletePopularPeopleUseCase(private val peopleService: PeopleService) {

    operator fun invoke() = peopleService.clearPopularPeople()
}