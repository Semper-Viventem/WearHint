package com.example.konstantin.weartest.ui

import me.dmdev.rxpm.navigation.NavigationMessage
import ru.semper_viventem.common.dto.Hint


class Back : NavigationMessage
class OpenHintItem(val hint: Hint) : NavigationMessage
class OpenAddHintScreen: NavigationMessage
class OpenCapabilitiesInformationScreen: NavigationMessage
class OpenHintList: NavigationMessage