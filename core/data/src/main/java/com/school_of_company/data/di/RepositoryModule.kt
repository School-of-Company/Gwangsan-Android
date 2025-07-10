package com.school_of_company.data.di

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.auth.AuthRepositoryImpl
import com.school_of_company.data.repository.local.LocalRepository
import com.school_of_company.data.repository.local.LocalRepositoryImpl
import com.school_of_company.data.repository.member.MemberRepository
import com.school_of_company.data.repository.member.MemberRepositoryImpl
import com.school_of_company.data.repository.notice.NoticeRepository
import com.school_of_company.data.repository.notice.NoticeRepositoryImpl
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.data.repository.post.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository

    @Binds
    abstract fun bindLocalRepository(
        localRepositoryImpl: LocalRepositoryImpl
    ): LocalRepository

    @Binds
    abstract fun bindMemberRepository(
        memberRepositoryImpl: MemberRepositoryImpl
    ): MemberRepository

    @Binds
    abstract fun bindNoticeRepository(
        noticeRepositoryImpl: NoticeRepositoryImpl
    ) : NoticeRepository
}