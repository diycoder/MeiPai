package com.mumu.meipai.util;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mumu.meipai.bean.CommentEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MuMu on 2017/01/06.
 */

public class SpanUtil {

    private SpanUtil() {

    }

    public static class PatternString {
        /**
         * #号括起来的话题#
         */
        public static final String TOPIC_PATTERN = "#[^#]+#";

        /**
         * 表情[大笑]
         */
        public static final String EXPRESSION_PATTERN = "\\[[^\\]]+\\]";

        /**
         * 网址
         */
        public static final String URL_PATTERN = "(([hH]ttp[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";

    }


    /**
     * 设置字体大小
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @param fontSize
     * @return
     */
    public static SpannableString setTextSize(String content, int startIndex, int endIndex, int fontSize) {
        if (TextUtils.isEmpty(content) || fontSize <= 0 || startIndex >= endIndex || startIndex < 0 || endIndex >= content.length()) {
            return null;
        }
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new AbsoluteSizeSpan(fontSize), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 截取某一段内容
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static SpannableString setTextSub(String content, int startIndex, int endIndex) {
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
            return null;
        }

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new SubscriptSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置下划线
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static SpannableString setTextUnderline(String content, int startIndex, int endIndex) {
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
            return null;
        }

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置加粗
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static SpannableString setTextBold(String content, int startIndex, int endIndex) {
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
            return null;
        }

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置斜体
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static SpannableString setTextItalic(String content, int startIndex, int endIndex) {
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
            return null;
        }

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置斜体加粗
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static SpannableString setTextBoldItalic(String content, int startIndex, int endIndex) {
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
            return null;
        }

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置前景色(字体颜色)
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @param foregroundColor
     * @return
     */
    public static SpannableString setTextForeground(String content, int startIndex, int endIndex, int foregroundColor) {
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
            return null;
        }

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(foregroundColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 设置背景色
     *
     * @param content
     * @param startIndex
     * @param endIndex
     * @param backgroundColor
     * @return
     */
    public static SpannableString setTextBackground(String content, int startIndex, int endIndex, int backgroundColor) {
        if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
            return null;
        }

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new BackgroundColorSpan(backgroundColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


    /**
     * 关键词变色处理
     *
     * @param str
     * @param patterStr 需要变色的关键词 或者 正则表达式
     * @return
     */
    public static SpannableString getKeyWordSpan(int color, String str, String patterStr) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten = Pattern.compile(patterStr, Pattern.CASE_INSENSITIVE);
        dealPattern(color, spannableString, patten, 0);
        return spannableString;
    }


    public static SpannableString getAtUserSpan(int color, String str, boolean clickable, Context context, Intent intent, List<CommentEntity> commentList) {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten;
        for (CommentEntity comment : commentList) {
            if (comment.user != null) {
                patten = Pattern.compile("@" + comment.user.screen_name, Pattern.CASE_INSENSITIVE);
                if (clickable) {
                    dealClick(spannableString, patten, 0, context, intent);
                }
                try {
                    dealPattern(color, spannableString, patten, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return spannableString;
    }


    /**
     * 自动识别话题并做颜色处理,可点击
     *
     * @param color
     * @param content
     */
    public static SpannableString getTopicSpan(int color, String content, boolean clickable, Context context, Intent intent) {
        SpannableString spannableString = new SpannableString(content);
        Pattern patten = Pattern.compile(PatternString.TOPIC_PATTERN, Pattern.CASE_INSENSITIVE);
        if (clickable) {
            dealClick(spannableString, patten, 0, context, intent);
        }
        try {
            dealPattern(color, spannableString, patten, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spannableString;
    }


    /**
     * 对spanableString进行正则判断，如果符合要求，则将内容变色
     *
     * @param color
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private static void dealPattern(int color, SpannableString spannableString, Pattern patten, int start) throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            //设置前景色span
            spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealPattern(color, spannableString, patten, end);
            }
            break;
        }
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，将内容设置可点击
     *
     * @param spannableString
     * @param patten
     * @param start
     */
    private static void dealClick(final SpannableString spannableString, Pattern patten, int start, final Context context, final Intent intent) {
        final Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            // 计算该内容的长度，也就是要替换的字符串的长度
            final int end = matcher.start() + key.length();
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    switchIntent((TextView) widget, matcher, intent, context);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置画笔属性
                    ds.setUnderlineText(false);//默认有下划线
                }
            }, matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealClick(spannableString, patten, end, context, intent);
            }
            break;
        }
    }

    /**
     * 处理不同关键字的点击事件
     *
     * @param widget
     * @param matcher
     * @param intent
     * @param context
     */
    private static void switchIntent(TextView widget, Matcher matcher, Intent intent, Context context) {
        TextView textView = widget;
        String content = textView.getText().toString().substring(matcher.start(), matcher.end());
        Log.e("subContent: ", content);
        if (!TextUtils.isEmpty(content)) {
            if (content.contains("#")) {//话题
                intent.putExtra("topic", content.replaceAll("#", ""));
            } else if (content.contains("@")) {//@用户
                intent.putExtra("userName", content.replaceAll("@", ""));
            }
            context.startActivity(intent);
        }
    }

}
