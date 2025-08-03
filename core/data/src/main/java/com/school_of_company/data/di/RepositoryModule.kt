package com.school_of_company.data.di

import com.school_of_company.data.repository.alert.AlertRepository
import com.school_of_company.data.repository.alert.AlertRepositoryImpl
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.auth.AuthRepositoryImpl
import com.school_of_company.data.repository.chat.ChatRepository
import com.school_of_company.data.repository.chat.ChatRepositoryImpl
import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.data.repository.image.ImageRepositoryImpl
import com.school_of_company.data.repository.local.LocalRepository
import com.school_of_company.data.repository.local.LocalRepositoryImpl
import com.school_of_company.data.repository.member.MemberRepository
import com.school_of_company.data.repository.member.MemberRepositoryImpl
import com.school_of_company.data.repository.notice.NoticeRepository
import com.school_of_company.data.repository.notice.NoticeRepositoryImpl
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.data.repository.post.PostRepositoryImpl
import com.school_of_company.data.repository.report.ReportRepository
import com.school_of_company.data.repository.report.ReportRepositoryImpl
import com.school_of_company.data.repository.review.ReviewRepository
import com.school_of_company.data.repository.review.ReviewRepositoryImpl
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

    @Binds
    abstract fun bindReportRepository(
        reportRepositoryImpl: ReportRepositoryImpl
    ) : ReportRepository

    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ) : ImageRepository

    @Binds
    abstract fun bindReviewRepository(
        reviewRepositoryImpl: ReviewRepositoryImpl
    ) : ReviewRepository

    @Binds
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ) : ChatRepository

    @Binds
    abstract fun bindAlertRepository(
        alertRepositoryImpl: AlertRepositoryImpl
    ) : AlertRepository

}