package com.fooddiary.presentation.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fooddiary.R
import com.fooddiary.databinding.ItemReportHistoryBinding
import java.time.format.DateTimeFormatter

/**
 * Adapter for displaying report history in RecyclerView
 * Features: Report preview, sharing, deletion
 */
class ReportHistoryAdapter(
    private val onReportClick: (MedicalReport) -> Unit,
    private val onShareClick: (MedicalReport) -> Unit,
    private val onDeleteClick: (MedicalReport) -> Unit
) : ListAdapter<MedicalReport, ReportHistoryAdapter.ReportHistoryViewHolder>(ReportDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHistoryViewHolder {
        val binding = ItemReportHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReportHistoryViewHolder(
        private val binding: ItemReportHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(report: MedicalReport) {
            binding.apply {
                // Set report name and type
                tvReportName.text = report.title

                // Set report details
                val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
                val startDate = report.dateRange.startDate.format(formatter)
                val endDate = report.dateRange.endDate.format(formatter)
                val createdDate = report.createdDate.format(formatter)

                tvReportDetails.text = "$startDate - $endDate • Created $createdDate"

                // Set report type icon
                ivReportTypeIcon.setImageResource(
                    when (report.type) {
                        ReportType.COMPREHENSIVE -> R.drawable.ic_description
                        ReportType.SYMPTOM_FOCUSED -> R.drawable.ic_favorite
                        ReportType.CORRELATION_ANALYSIS -> R.drawable.ic_insights
                        ReportType.MEDICAL_SUMMARY -> R.drawable.ic_local_hospital
                    }
                )

                // Set click listeners
                root.setOnClickListener {
                    onReportClick(report)
                }

                btnShareReport.setOnClickListener {
                    onShareClick(report)
                }

                btnDeleteReport.setOnClickListener {
                    onDeleteClick(report)
                }
            }
        }
    }

    private class ReportDiffCallback : DiffUtil.ItemCallback<MedicalReport>() {
        override fun areItemsTheSame(oldItem: MedicalReport, newItem: MedicalReport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MedicalReport, newItem: MedicalReport): Boolean {
            return oldItem == newItem
        }
    }
}