package com.twitty.feature.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.twitty.feature.tag.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingItems: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigationList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setNavigationList() {
        settingItems = listOf(
            getString(R.string.alarm_setting),
            getString(R.string.tag_management),
            getString(R.string.backup_and_sync),
            getString(R.string.theme),
            getString(R.string.user_feedback_and_improvement),
            getString(R.string.reset_settings)
        )

        binding.lvSettings.adapter = ArrayAdapter(
            requireContext(), R.layout.item_settings, R.id.tv_settings_item, settingItems
        )

        binding.lvSettings.setOnItemClickListener { _, _, position, _ ->
            navigateToFragment(settingItems[position])
        }
    }

    private fun navigateToFragment(selected: String) {
//        val action = when (selected) {
//            getString(R.string.tag_management) -> R.id.action_settingsFragment_to_tagManagementFragment
//            else -> R.id.action_settingsFragment_to_tagManagementFragment
//        }
//        findNavController().navigate(action)
    }
}