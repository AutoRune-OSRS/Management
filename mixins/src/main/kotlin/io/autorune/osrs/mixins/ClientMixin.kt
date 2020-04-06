package io.autorune.osrs.mixins

import io.autorune.osrs.api.Client
import io.autorune.osrs.api.collection.HashTable
import io.autorune.osrs.api.widget.Widget
import io.autorune.osrs.api.widget.WidgetNode
import io.autorune.osrs.mixin.*

abstract class ClientMixin : Client
{

    @JvmField
    val isDebugMenuActionsEnabledMixin: Boolean = true

    @InsertionMixin
    fun drawInterfaceMixin(widgets: Array<Widget>, parentId : Int, minX : Int, minY : Int, maxX: Int, maxY: Int, x: Int, y: Int, var8: Int)
    {

        val componentTable : HashTable = clientInstance.widgetNodeCache

        for(rlWidget in widgets)
        {
            val widget : Widget = rlWidget
            if(widget.parentHash != parentId || widget.isHidden)
            {
                continue
            }
            if(parentId != -1)
            {
                widget.renderParentId = parentId
            }
            val renderX : Int = x + widget.x
            val renderY : Int = y + widget.y
            widget.renderX = renderX
            widget.renderY = renderY
            var childNode = componentTable.fetch(widget.widgetHash.toLong())
            if(childNode != null)
            {
                childNode = childNode as WidgetNode
                val widgetId : Int = widget.widgetHash
                val groupId : Int = childNode.group
                val children : Array<Widget> = clientInstance.widgets[groupId]
                for(child in children)
                {
                    if(child.parentHash == -1)
                    {
                        child.renderParentId = widgetId
                    }
                }
            }
        }
    }

    fun getWidgetMixin(groupId : Int, childId : Int) : Widget?
    {
        val widgets : Array<Array<Widget>>? = clientInstance.widgets
        if(widgets == null || widgets.size <= groupId)
        {
            return null
        }
        val childWidgets : Array<Widget>? = widgets[groupId]
        return if(childWidgets == null || childWidgets.size <= childId)
        {
            null
        }
        else childWidgets[childId]
    }

	fun realMenuActionsMixin() : Array<String>
	{

		val actions = menuActions

		val targets = arrayOfNulls<String>(menuOptionsCount)

		if(actions.size < targets.size) return emptyArray()

		val nonNullActions = mutableListOf<String>()

		if(actions.isNotEmpty())
		{
			targets.forEachIndexed { index, _ ->

				val menuAction = actions[index]

				if(menuAction != null)
					nonNullActions.add(index, menuAction)

			}
		}

		return nonNullActions.toTypedArray().reversedArray()

	}

    @InsertionMixin
	fun sendMenuActionMixin(param0: Int, param1: Int, param2: Int, param3: Int,
							param4: String, param5: String, param6: Int, param7: Int)
	{

		if (clientInstance.isDebugMenuActionsEnabled) {
			println(String.format("%s , %s , %s , %s , %s , %s , %s , %s",
					param0, param1, param2, param3, param4, param5, param6, param7))
		}

	}

	fun initClassLoaderMixin(vanillaGamepackLocation: String)
	{

	}

	fun initCallbackMixin()
	{

	}

}