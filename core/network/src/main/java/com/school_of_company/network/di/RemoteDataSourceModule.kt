package com.school_of_company.network.di

import com.school_of_company.network.datasource.auth.AuthDataSource
import com.school_of_company.network.datasource.auth.AuthDataSourceImpl
import com.school_of_company.network.datasource.member.MemberDataSource
import com.school_of_company.network.datasource.member.MemberDataSourceImpl
import com.school_of_company.network.datasource.notice.NoticeDataSource
import com.school_of_company.network.datasource.notice.NoticeDataSourceImpl
import com.school_of_company.network.datasource.post.PostDataSource
import com.school_of_company.network.datasource.post.PostDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun bindPostDataSource(
        postDataSourceImpl: PostDataSourceImpl
    ): PostDataSource

    @Binds
    abstract fun bindMemberDataSource(
        memberDataSourceImpl: MemberDataSourceImpl
    ): MemberDataSource

    @Binds
    abstract fun bindNoticeDataSource(
        noticeDataSourceImpl: NoticeDataSourceImpl
    ) : NoticeDataSource
}