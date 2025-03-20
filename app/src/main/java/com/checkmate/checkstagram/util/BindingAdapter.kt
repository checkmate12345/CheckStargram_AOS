package com.checkmate.checkstagram.util

import androidx.databinding.BindingAdapter
import com.google.android.material.switchmaterial.SwitchMaterial

@BindingAdapter("bindChecked")
fun bindChecked(switch: SwitchMaterial, isChecked: Boolean?) {
    isChecked?.let {
        if (switch.isChecked != it) {  // 값이 다를 때만 업데이트
            switch.isChecked = it
        }
    }
}