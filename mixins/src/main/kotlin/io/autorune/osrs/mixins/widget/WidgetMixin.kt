package io.autorune.osrs.mixins.widget

import io.autorune.osrs.api.widget.Widget
import io.autorune.osrs.api.widget.WidgetNode
import java.awt.Rectangle
import java.util.*


abstract class WidgetMixin : Widget
{

	@JvmField
	val renderParentIdMixin: Int = -1

	@JvmField
	val renderXMixin: Int = -1

	@JvmField
	val renderYMixin: Int = -1

	fun boundsMixin() : Rectangle
	{
		return Rectangle(renderXMixin, renderYMixin, width, height)
	}

	fun getChildMixin(index : Int) : Widget?
	{
		val widgets : Array<Widget?>? = children
		return if(widgets == null || widgets[index] == null)
		{
			null
		}
		else widgets[index]
	}

	fun getParentMixin() : Widget?
	{
		val id = parentHash
		return if(id == -1)
		{
			null
		}
		else clientInstance.getWidget(id ushr 16, id and 0xFFFF)
	}

	fun isVisibleMixin() : Boolean
	{

		if(isHidden)
		{
			return false
		}
		if(parent == null)
		{
			if(widgetHash ushr 16 != clientInstance.rootWidget) return true
		}
		else if(parent.isVisible) return true
		return false
	}

	fun parentIdMixin() : Int
	{
		return parentHash ushr 16
	}

	fun widgetIdMixin() : Int
	{
		return widgetHash and 0xFFFF
	}

	fun getDynamicChildrenMixin() : Array<Widget>
	{
		val children = children ?: return emptyArray()
		val widgets = mutableListOf<Widget>()
		for(widget in children)
		{
			if(widget != null && widget.parentHash == widgetHash)
			{
				widgets.add(widget)
			}
		}
		return widgets.toTypedArray()
	}

	fun getStaticChildrenMixin() : Array<Widget>
	{
		if(parentHash == widgetHash) return emptyArray()
		val widgets : MutableList<Widget> = ArrayList()
		for(widget in clientInstance.widgets[widgetHash ushr 16])
		{
			if(widget != null && widget.parentHash == widgetHash)
			{
				widgets.add(widget)
			}
		}
		return widgets.toTypedArray()
	}

	fun getNestedChildrenMixin() : Array<Widget>
	{
		if(parentHash == widgetHash) return emptyArray()
		val componentTable = clientInstance.widgetNodeCache
		val node = componentTable.fetch(widgetHash.toLong()) ?: return emptyArray()
		val widgetNode = node as WidgetNode
		val group : Int = widgetNode.group
		val widgets = mutableListOf<Widget>()
		for(widget in clientInstance.widgets[group])
		{
			if(widget != null && widget.parentHash == -1)
			{
				widgets.add(widget)
			}
		}
		return widgets.toTypedArray()
	}

}