package com.example.konstantin.weartest.ui

import me.dmdev.rxpm.navigation.NavigationMessage
import ru.semper_viventem.common.dto.Hint


class OpenImage(val imageUrl: String): NavigationMessage

class OpenHintDetails(hint: Hint): NavigationMessage