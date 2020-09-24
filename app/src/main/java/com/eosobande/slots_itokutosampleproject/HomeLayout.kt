package com.eosobande.slots_itokutosampleproject

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.Toast
import com.eosobande.itokuto.*
import com.eosobande.itokuto.modifiers.Widget
import com.eosobande.itokuto.modifiers.Widget.Companion.MATCH
import com.eosobande.itokuto.params.ConstraintParams
import com.eosobande.itokuto.params.LStackParams
import com.eosobande.itokuto.widgets.image.Image
import com.eosobande.itokuto.widgets.stacks.ConstraintStack
import com.eosobande.itokuto.widgets.stacks.linear.HStack
import com.eosobande.itokuto.widgets.text.Text
import com.eosobande.itokuto.widgets.text.button.Button
import java.util.*

class HomeLayout(override val context: Context) : Widget {

    private val random = Random()

    private val credits = State<Int>()
    private val creditsText = State<String>()
    private val jackPot = State(false)

    private val numbers: Array<Int> = Array(3) { 0 }
    private val symbols: Array<Drawable> = arrayOf(
        drawable(R.drawable.ic_apple)!!,
        drawable(R.drawable.ic_cherry)!!,
        drawable(R.drawable.ic_star)!!
    )
    private val slotStates: List<State<Drawable?>> = symbols.map { State(it) }

    init {
        credits {
            creditsText("Credits: $it")
        }
        credits(1000)
    }

    override val widget: Widget
        get() =
            ConstraintStack(context)
                .background(Colors.deepYellow)
                .invoke {

                    +HStack(context)
                        .id(R.id.header)
                        .gravity(Gravity.CENTER)
                        .params(
                            ConstraintParams()
                                .topToTop(0)
                                .bottomToTop(R.id.credits)
                                .startToStart(0)
                                .endToEnd(0)
                        )
                        .invoke {

                            +Image(context, drawable(R.drawable.ic_star_fill))
                                .imageTint(Colors.yellow.colorState)

                            +Text(context, "Itokuto Slots")
                                .textColor(Color.WHITE.colorState)
                                .bold()
                                .fontSize(24.sp)
                                .params(
                                    LStackParams()
                                        .margin(horizontal = 20.dp)
                                )

                            +Image(context, drawable(R.drawable.ic_star_fill))
                                .imageTint(Colors.yellow.colorState)

                        }

                    +Text(context, creditsText)
                        .id(R.id.credits)
                        .padding(10.dp)
                        .textColor(Color.BLACK.colorState)
                        .params(
                            ConstraintParams()
                                .topToBottom(R.id.header)
                                .bottomToTop(R.id.slots)
                                .startToStart(0)
                                .endToEnd(0)
                        )
                        .background(
                            Rectangle()
                                .rounded()
                                .solidColor(Colors.whiteX50)
                        )

                    +HStack(context)
                        .id(R.id.slots)
                        .padding(start = 10.dp)
                        .params(
                            ConstraintParams(MATCH)
                                .topToBottom(R.id.credits)
                                .bottomToTop(R.id.spin)
                                .startToStart(0)
                                .endToEnd(0)
                        )
                        .weightSum(3f)
                        .invoke {
                            numbers.indices.forEach {
                                +SlotItem(context, slotStates[it], jackPot)
                            }
                        }

                    +Button(context, "Spin")
                        .id(R.id.spin)
                        .textColor(Color.WHITE.colorState)
                        .padding(10.dp)
                        .bold()
                        .onClick { spin() }
                        .params(
                            ConstraintParams()
                                .topToBottom(R.id.slots)
                                .bottomToBottom(0)
                                .startToStart(0)
                                .endToEnd(0)
                        )
                        .background(
                            Rectangle()
                                .rounded()
                                .solidColor(Colors.pink)
                        )

                }

    private fun spin() {

        credits(credits() - spinCost)
        numbers.indices.forEach {
            numbers[it] = random.nextInt(numbers.size)
            slotStates[it](symbols[numbers[it]])
        }

        jackPot(numbers[0] == numbers[1] && numbers[1] == numbers[2])
        if (jackPot()) {
            credits(credits() + reward)
            Toast.makeText(context, "JACKPOT!!!!!", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {

        const val spinCost = 5
        const val reward = 50

    }

}


