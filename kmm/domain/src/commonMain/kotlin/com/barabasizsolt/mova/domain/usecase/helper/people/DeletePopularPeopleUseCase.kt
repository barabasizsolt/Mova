package com.barabasizsolt.mova.domain.usecase.helper.people

import com.barabasizsolt.mova.people.api.PeopleService

class DeletePopularPeopleUseCase(private val peopleService: PeopleService) {

    operator fun invoke() = peopleService.clearPopularPeople()
}