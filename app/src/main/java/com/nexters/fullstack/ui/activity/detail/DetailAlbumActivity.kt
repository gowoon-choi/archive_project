package com.nexters.fullstack.ui.activity.detail

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.nexters.fullstack.BuildConfig
import com.nexters.fullstack.util.Constants
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.data.db.entity.ImageModel
import com.nexters.fullstack.databinding.ActivityDetailAlbumBinding
import com.nexters.fullstack.presentaion.viewmodel.detail.DetailAlbumViewModel
import com.nexters.fullstack.ui.widget.ImageDialog
import com.nexters.fullstack.util.extension.share
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class DetailAlbumActivity : BaseActivity<ActivityDetailAlbumBinding, DetailAlbumViewModel>() {

    override val layoutRes: Int = R.layout.activity_detail_album
    override val viewModel: DetailAlbumViewModel by viewModel<DetailAlbumViewModel>()
    private val localImage: ImageModel?
        get() = intent.getParcelableExtra(Constants.DETAIL_IMAGE) as? ImageModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            it.localImageData = localImage
            it.viewModels = viewModel
            it.executePendingBindings()
        }

        setObserve()

        setOnClickListener()

        viewModel.fetchImage(localImage?.imageId ?: throw IllegalAccessException("not found id"))

    }

    private fun setObserve() {
        with(viewModel.output) {
            finish().observe(this@DetailAlbumActivity) {
                this@DetailAlbumActivity.finish()
            }
            getIsLocalContain().observe(this@DetailAlbumActivity) { contain ->
                val drawable = ContextCompat.getDrawable(
                    this@DetailAlbumActivity,
                    if (contain) R.drawable.ic_ico_already_heart else R.drawable.ic_ico_heart
                )

                binding.ivFavorite.setImageDrawable(drawable)
            }
            showDeleteDialog().observe(this@DetailAlbumActivity) {
                
                ImageDialog.setTitle(it.title)
                    .setImageUrl(it.imageUrl)
                    .setPositiveClickListener(it.onPositiveClickListener)
                    .setNegative(it.negative)
                    .setPositive(it.positive)
                    .build()
                    .show(supportFragmentManager, this@DetailAlbumActivity.javaClass.name)

            }
            share().observe(this@DetailAlbumActivity) {

                val intent = Intent().share(
                    it.type,
                    SHARE_TITLE,
                    FileProvider.getUriForFile(
                        this@DetailAlbumActivity,
                        BuildConfig.APPLICATION_ID,
                        File(it.imageUrl)
                    )
                )

                startActivity(intent)
            }
        }
    }

    private fun setOnClickListener() {
        binding.frameFavorite.setOnClickListener {
            viewModel.input.favorite(
                localImage?.imageId ?: throw IllegalAccessException("not found id")
            )
        }
    }

    companion object {

        private const val SHARE_TITLE = "share"

    }
}
