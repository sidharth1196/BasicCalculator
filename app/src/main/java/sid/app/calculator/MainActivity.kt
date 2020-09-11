package sid.app.calculator

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    class Stack{
        private val elements : MutableList<Any> = mutableListOf()
        fun isEmpty() : Boolean {
            return elements.isEmpty()
        }
        fun peek() : Any ? {
            return elements.lastOrNull()
        }
        fun pop(): Any ? {
            val item = elements.lastOrNull()
            if(elements.isNotEmpty()){
                elements.removeAt(elements.size - 1)
            }
            return item
        }
        fun push(item : Any) {
            elements.add(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        answerTextView.visibility = View.INVISIBLE
        zeroButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() + "0"
        }
        oneButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "1"
        }
        twoButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "2"
        }
        threeButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "3"
        }
        fourButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "4"
        }
        fiveButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "5"
        }
        sixButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "6"
        }
        sevenButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "7"
        }
        eightButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "8"
        }
        nineButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() +  "9"
        }
        clearButton.setOnClickListener {
            termTextView.text = ""
            answerTextView.text = ""
            answerTextView.visibility = View.INVISIBLE
        }
        sumButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() + "+"
        }
        subButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() + "-"
        }
        mulButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() + "*"
        }
        divButton.setOnClickListener {
            termTextView.text = termTextView.text.toString() + "/"
        }
        equalButton.setOnClickListener {
            var term = termTextView.text.toString()
            if(term.isNotEmpty()){
                var answer = evaluateTerm(term)
                answerTextView.text = "O/P: ${String.format("%.2f", answer)}"
                answerTextView.visibility = View.VISIBLE
            }
        }
    }
    private fun checkOperator (item : Char) : Boolean{
        if(item == '+' || item == '-' || item == '*' || item == '/' )
            return true
        return false
    }
    private fun checkPrecedence(item:Char) : Int {
        if (item =='+' || item =='-')
            return 1
        else if(item == '*' || item == '/')
            return 2
        else
            return 0
    }
    private fun calculateOperator(operator : Stack, operand : Stack) : Double{
        var x : Double = operand.pop().toString().toDouble()
        var y : Double = operand.pop().toString().toDouble()
        var op : Char = operator.pop().toString()[0]
        if (op == '+') {
            return x+y
        } else if (op =='-') {
            return y-x
        } else if (op == '*') {
            return x*y
        } else if(op == '/') {
            return y/x
        } else {
            return  0.0
        }
    }
    private fun evaluateTerm (term : String) : Double{
        var operand = Stack()
        var operator = Stack()
        var answer : Double
        var formattedTerm : String
        var num : Int = 0
        if(checkOperator(term[term.length-1])){
            formattedTerm = term.dropLast(1)
        }
        else{
            formattedTerm = term
        }
        for(c in formattedTerm.indices){
            if (formattedTerm[c].isDigit()){
                num = num*10 + Character.getNumericValue(formattedTerm[c])
                //operand.push(term[c])
            }
            else if(checkOperator(formattedTerm[c])){
                operand.push(num)
                num = 0
                while(!operator.isEmpty() && checkPrecedence(formattedTerm[c]) <= checkPrecedence(operator.peek().toString()[0])){
                    operand.push(calculateOperator(operator, operand))
                }
                operator.push(formattedTerm[c])
            }
        }
        operand.push(num)
        while(!operator.isEmpty()){
            operand.push(calculateOperator(operator, operand))
        }
        answer = operand.peek().toString().toDouble()
        return  answer
    }
}