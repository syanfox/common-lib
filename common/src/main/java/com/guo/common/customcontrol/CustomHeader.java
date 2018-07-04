package com.guo.common.customcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guo.common.R;

/**
 * 自定义标题
 */
public class CustomHeader extends LinearLayout {

    private static final String TAG = CustomHeader.class.getSimpleName();
    /**
     * 右边不显示
     */
    public static final int RIGHT_NO = 0;
    /**
     * 左边不显示
     */
    public static final int LEFT_NO = 0;
    /**
     * 右边按钮
     */
    public static final int RIGHT_IMG = 1;
    /**
     * 显示可点击的文字
     */
    public static final int RIGHT_TEXT = 2;

    /**
     * 显示返回按钮
     */
    public static final int LEFT_IMG = 1;
    /**
     * 在返回按钮位置显示文字而不是图标
     */
    public static final int LEFT_TEXT = 2;

    /**
     * 左边按钮
     */
    private ImageView mImgLeft;
    /**
     * 左边可点击文字
     */
    private TextView mTvLeft;
    /**
     * 中间按钮
     */
    private ImageView mImgMiddle;

    /**
     * 右边按钮
     */
    private ImageView mImgRight;
    /**
     * 右边可点击文字
     */
    private TextView mTvRight;
    /**
     * 中间标题
     */
    private TextView mTitle;
    private FrameLayout mFrameLayout;
    private int mLeftType;
    private int mMiddleType;
    private int mRightSrc;
    private int mMiddleSrc;
    private int mLeftSrc;
    private int mBackground;
    private int mRightType;
    private String mRightText;
    private String mLeftText;
    private String mTitleText;


    public CustomHeader(Context context) {
        super(context);

    }

    public CustomHeader(Context context, AttributeSet attrs) {

        super(context, attrs);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomHeader);
        mLeftType = typeArray.getInteger(R.styleable.CustomHeader_left, 1);
        mRightType = typeArray.getInteger(R.styleable.CustomHeader_right, 1);
        mMiddleType = typeArray.getInteger(R.styleable.CustomHeader_middle, 0);
        mBackground = typeArray.getResourceId(R.styleable.CustomHeader_backgroundColor, R.color.color_028CE6);
        mLeftSrc = typeArray.getResourceId(R.styleable.CustomHeader_src_left, R.drawable.selector_head_return);
        mRightSrc = typeArray.getResourceId(R.styleable.CustomHeader_src_right, R.drawable.selector_head_return);
        mMiddleSrc = typeArray.getResourceId(R.styleable.CustomHeader_src_middle, R.drawable.selector_head_return);
        mLeftText = typeArray.getString(R.styleable.CustomHeader_text_left);
        mRightText = typeArray.getString(R.styleable.CustomHeader_text_right);
        mTitleText = typeArray.getString(R.styleable.CustomHeader_text_title);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_header, null);
        mImgLeft = (ImageView) view.findViewById(R.id.header_img_left);
        mImgMiddle = (ImageView) view.findViewById(R.id.header_img_middle);
        mTvLeft = (TextView) view.findViewById(R.id.header_tv_left);
        mTitle = (TextView) view.findViewById(R.id.header_tv_title);
        mTvRight = (TextView) view.findViewById(R.id.header_tv_right);
        mImgRight = (ImageView) view.findViewById(R.id.header_img_right);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.header_fl_right);
        if (!TextUtils.isEmpty(mTitleText)) {
            mTitle.setText(mTitleText);
        } else {
            mTitle.setText("标题");
        }
        mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);//如果标题长度超过控件显示范围，则以跑马灯效果滚动。
        mTitle.setSelected(true);//需要获得焦点才有跑马灯效果
        view.setBackgroundResource(mBackground);
        init();
        typeArray.recycle();

        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
    }


    private void init() {

        switch (mRightType) {

            case RIGHT_NO:
                mTvRight.setVisibility(View.INVISIBLE);
                mFrameLayout.setVisibility(View.INVISIBLE);
                break;
            case RIGHT_TEXT:
                mImgRight.setVisibility(View.GONE);
                mTvRight.setVisibility(View.VISIBLE);
                mTvRight.setText(mRightText);
                break;
            case RIGHT_IMG:
                mTvRight.setVisibility(View.GONE);
                mImgRight.setVisibility(View.VISIBLE);
                mImgRight.setImageResource(mRightSrc);
                break;
            default:
                mTvRight.setVisibility(View.GONE);
                mImgRight.setVisibility(View.VISIBLE);
                mImgRight.setImageResource(R.drawable.selector_head_return);
                break;
        }

        switch (mLeftType) {
            case LEFT_NO:
                mTvLeft.setVisibility(View.GONE);
                mImgLeft.setVisibility(View.INVISIBLE);
                break;
            case LEFT_TEXT:
                mTvLeft.setVisibility(View.VISIBLE);
                mImgLeft.setVisibility(View.GONE);
                mTvLeft.setText(mLeftText);
                break;
            case LEFT_IMG:
                mTvLeft.setVisibility(View.GONE);
                mImgLeft.setVisibility(View.VISIBLE);
                mImgLeft.setImageResource(mLeftSrc);
                break;
            default:
                mTvLeft.setVisibility(View.GONE);
                mImgLeft.setVisibility(View.VISIBLE);
                mImgLeft.setImageResource(R.drawable.selector_head_return);
                break;
        }

        switch (mMiddleType) {
            case 1:
                mImgMiddle.setVisibility(VISIBLE);
                mImgMiddle.setImageResource(mMiddleSrc);
                break;
            case 0:
            default:
                mImgMiddle.setVisibility(GONE);
                break;
        }

    }

    public void setHeaderClickListener(final OnHeaderButtonClickListener clickListener) {

        getLeftView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onLeftButtonClick();
            }
        });

        getRightView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onRightButtonClick();
            }
        });

        mImgMiddle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onMiddleButtonClick();
            }
        });
    }



    private View getLeftView() {
        switch (mLeftType) {
            case LEFT_TEXT:
                return mTvLeft;
            case LEFT_IMG:
            default:
                return mImgLeft;
        }
    }

    private View getRightView() {
        switch (mRightType) {
            case RIGHT_TEXT:
                return mTvRight;
            case RIGHT_IMG:
            default:
                return mImgRight;
        }
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setRightTitle(String title) {
        mTvRight.setText(title);
    }

    public ImageView getImgRight() {
        return mImgRight;
    }

    public void setRightVisibility(int visibility) {
        switch (visibility) {
            case View.GONE:
                mFrameLayout.setVisibility(View.GONE);
                break;
            case View.VISIBLE:
                mFrameLayout.setVisibility(View.VISIBLE);
                if (mRightType == RIGHT_IMG) {
                    mImgRight.setVisibility(View.VISIBLE);
                } else {
                    mTvRight.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public void setLeftImageResource(int resourceId) {
        mImgLeft.setImageResource(resourceId);
    }

    public void setMiddleImageResource(int resourceId) {
        mImgLeft.setImageResource(resourceId);
    }

    public void setRightImageResource(int resourceId) {
        mFrameLayout.setVisibility(VISIBLE);
        mTvRight.setVisibility(GONE);
        mImgRight.setVisibility(VISIBLE);
        mImgRight.setImageResource(resourceId);
    }


    public interface OnHeaderButtonClickListener {
        void onLeftButtonClick();

        void onMiddleButtonClick();

        void onRightButtonClick();
    }

}
