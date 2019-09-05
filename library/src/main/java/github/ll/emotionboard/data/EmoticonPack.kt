package github.ll.emotionboard.data

import android.content.Context
import android.view.View
import github.ll.emotionboard.interfaces.OnEmoticonClickListener
import github.ll.emotionboard.interfaces.GridPageFactory
import github.ll.emotionboard.interfaces.PageFactory

/**
 * @author Liliang
 * Email: 53127822@qq.com
 * @date 2018/6/7
 *
 * 一个具体的表情包
 * 存在多种表情包的情况，所有使用工厂设计模式，来生产不同的表情包
 * 如：1.DeleteBtnPageFactory 2.GridPageFactory 3.BigIconTextPageFactory 4.TextPageFactory
 *
 */
class EmoticonPack<T: Emoticon> {
    var iconUri: String? = null // 表情切换tab显示的一个表情路径
    var name: String? = null
    lateinit var emoticons: MutableList<T>  // 一个表情包中所有的表情
    var pageFactory: PageFactory<T> = GridPageFactory() // 表情包工厂类
    var isDataChanged = false
    var tag: Any? = null

    val pageCount: Int
    get() {
        if (emoticons == null || pageFactory == null) {
            throw RuntimeException("must set emoticons and pageFactory first")
        }

        var count = emoticons.size / pageFactory.emoticonsCapacity()

        if (emoticons.size % pageFactory.emoticonsCapacity() > 0) {
            count++
        }

        return count
    }


    fun getView(context: Context, pageIndex: Int, listener: OnEmoticonClickListener<Emoticon>?): View {
        // 创建viewPager
        return pageFactory.create(context, getEmoticons(pageIndex), listener)
    }

    private fun getEmoticons(pageIndex: Int): List<T> {
        val fromIndex = pageIndex * pageFactory.emoticonsCapacity()
        var toIndex = Math.min((pageIndex + 1) * pageFactory.emoticonsCapacity(), emoticons.size)

        return emoticons.subList(fromIndex, toIndex)
    }
}