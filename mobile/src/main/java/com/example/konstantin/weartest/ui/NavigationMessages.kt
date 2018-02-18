package com.example.konstantin.weartest.ui

import com.example.konstantin.weartest.domain.dto.Hint
import me.dmdev.rxpm.navigation.NavigationMessage


class Back : NavigationMessage
class OpenHintItem(val hint: Hint) : NavigationMessage
class OpenAddHintScreen: NavigationMessage