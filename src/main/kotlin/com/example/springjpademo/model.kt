package com.example.springjpademo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import javax.persistence.*

@Entity
@Table(name = "WP_USER")
data class User(
        @Id
        @GeneratedValue
        val id: Long = 0,
        @Column(length = 200)
        var name: String,
        @Lob
        var description: String,
        @Embedded
        val profile: Profile,

        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "user")
        val socialInfos: MutableList<SocialInfo> = mutableListOf(),

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val answeredHistories: MutableList<AnsweredHistory> = mutableListOf()
)


@Embeddable
data class Profile(
        var phone: String,
        var address: String
)

@Entity
data class SocialInfo(
        @Id
        @GeneratedValue
        val id: Long = -1,
        @ManyToOne(fetch = FetchType.LAZY)
        val user: User? = null,
        val name: String
)

@Entity
data class AnsweredHistory(
        @Id
        val id: Long
)

interface UserRepository : CrudRepository<User, Long> {
    fun findByName(name: String): User
    fun findByNameAndId(name: String, id: Long)
    fun findAll(pageable: Pageable): Page<User>
}

interface SocialInfoRepository : JpaRepository<SocialInfo, Long> {

}


