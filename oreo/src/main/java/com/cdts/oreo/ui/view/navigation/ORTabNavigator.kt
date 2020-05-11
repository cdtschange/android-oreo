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

        //反射获取mBackStack mIsPendingBackStackOperation
        val mBackStackField = FragmentNavigator::class.java.getDeclaredField("mBackStack")
        mBackStackField.isAccessible = true
        var mBackStack: ArrayDeque<Int> = mBackStackField.get(this) as ArrayDeque<Int>
        val mIsPendingBackStackOperationField =
            FragmentNavigator::class.java.getDeclaredField("mIsPendingBackStackOperation")
        mIsPendingBackStackOperationField.isAccessible = true
        var mIsPendingBackStackOperation: Boolean = mIsPendingBackStackOperationField.get(this) as Boolean

        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.last().toInt() == destId)

        val isAdded: Boolean
        if (initialNavigation) {
            isAdded = true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                manager.popBackStack(
                    generateMyBackStackName(mBackStack.size, mBackStack.last()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                transaction.addToBackStack(generateMyBackStackName(mBackStack.size, destId))
                mIsPendingBackStackOperation = true
                mIsPendingBackStackOperationField.set(this, true)
            }
            isAdded = false
        } else {
            transaction.addToBackStack(generateMyBackStackName(mBackStack.size + 1, destId))
            mIsPendingBackStackOperation = true
            mIsPendingBackStackOperationField.set(this, true)
            isAdded = true
        }

        transaction.setReorderingAllowed(true)
        transaction.commit()

        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    private fun generateMyBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}

class ORTabNavHostFragment: NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return ORTabNavigator(requireContext(), childFragmentManager, id)
    }
}