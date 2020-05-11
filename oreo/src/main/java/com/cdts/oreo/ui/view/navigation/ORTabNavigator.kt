package com.cdts.oreo.ui.view.navigation

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

@Navigator.Name("or_tab_fragment")  // Use as custom tag at navigation.xml
class ORTabNavigator (
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    @ExperimentalStdlibApi
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        var fragment = manager.findFragmentByTag(tag)
        if (fragment == null) {
            var className = destination.className
            if (className[0] == '.') {
                className = context.packageName + className
            }
            fragment = instantiateFragment(context, manager, className, args)
            fragment.arguments = args
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.show(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()
        return destination
    }
}

class ORTabNavHostFragment: NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return ORTabNavigator(requireContext(), childFragmentManager, id)
    }
}