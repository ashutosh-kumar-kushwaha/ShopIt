package ashutosh.shopit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.databinding.QuestionAnswerItemBinding
import ashutosh.shopit.models.Question

class QuestionsAnswersAdapter(private val questions: List<Question>): RecyclerView.Adapter<QuestionsAnswersAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding : QuestionAnswerItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question){
            binding.questionTxtVw.text = question.question
            binding.answerTxtVw.text = question.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(QuestionAnswerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}