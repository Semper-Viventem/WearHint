package com.example.konstantin.weartest.ui

import com.example.konstantin.weartest.domain.dto.Hint
import me.dmdev.rxpm.navigation.NavigationMessage


class OpenImage(val imageUrl: String): NavigationMessage

class OpenHintDetails(hint: Hint): NavigationMessage