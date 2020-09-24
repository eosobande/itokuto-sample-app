package com.eosobande.slots_itokutosampleproject

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.eosobande.itokuto.*
import com.eosobande.itokuto.modifiers.Widget
import com.eosobande.itokuto.params.LStackParams
import com.eosobande.itokuto.widgets.image.Image

class SlotItem(
    override val context: Context,
    private val symbol: State<Drawable?>,
    private val jackPot: State<Boolean>
) : Widget {

    override val widget: Widget
        get() =
            Image(context, symbol)
                .adjustBounds()
                .scale(ImageView.ScaleType.FIT_CENTER)
                .padding(5.dp)
                .bind(jackPot) {
                    backgroundTint(if (it) Colors.greenX50.colorState else null)
                }
                .background(
                    Rectangle()
                        .solidColor(Colors.whiteX50)
                        .cornerRadius(10.dp)
                )
                .params(
                    LStackParams(0, weight = 1f)
                        .margin(end = 10.dp)
                )

}