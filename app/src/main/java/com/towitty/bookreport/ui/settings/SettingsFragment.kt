package com.towitty.bookreport.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.towitty.bookreport.R
import com.towitty.bookreport.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingItems: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        settingItems = listOf(
            getString(R.string.alarm_setting),
            getString(R.string.tag_management),
            getString(R.string.backup_and_sync),
            getString(R.string.theme),
            getString(R.string.user_feedback_and_improvement),
            getString(R.string.reset_settings)
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_settings,
            R.id.tv_settings_item,
            settingItems
        )
        binding.lvSettings.adapter = adapter

        binding.lvSettings.setOnItemClickListener { _, _, position, id ->
//            val selectedSetting = settingItems[position]
//            when (selectedSetting) {
//                getString(R.string.alarm_setting) -> navigateToFragment(AlarmSettingFragment())
//                getString(R.string.tag_management) -> navigateToFragment(TagManagementFragment())
//                getString(R.string.backup_and_sync) -> navigateToFragment(BackupAndSyncFragment())
//                getString(R.string.theme) -> navigateToFragment(ThemeFragment())
//                getString(R.string.user_feedback_and_improvement) -> navigateToFragment(UserFeedbackFragment())
//                getString(R.string.reset_settings) -> navigateToFragment(ResetSettingsFragment())
//            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun navigateToFragment(fragment: Fragment) {
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }
}