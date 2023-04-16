package people.model

import com.barabasizsolt.mova.pager.PagerItem

data class People(
    override val id: String,
    val adult: Boolean,
    val profilePath: String,
    val name: String
) : PagerItem