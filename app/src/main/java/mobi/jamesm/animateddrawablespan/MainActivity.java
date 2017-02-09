package mobi.jamesm.animateddrawablespan;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mTextView = ((TextView) findViewById(R.id.textview_animated_drawable));
        mTextView.setText(getLiveIconFormattedString((TextView) findViewById(R.id.textview_animated_drawable)));

    }

    public SpannableStringBuilder getLiveIconFormattedString(final TextView pTextView) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append("  FLASHING DOT ");
        stringBuilder.setSpan(new AnimatedDrawableSpan((AnimationDrawable)
                mContext.getResources().getDrawable(R.drawable.live_icon_animation),
                AnimatedDrawableSpan.ALIGN_BASELINE, (int) (pTextView.getLineHeight() * 0.7), 50,
                new AnimatedDrawableSpan.UpdateListener() {
                    @Override
                    public void update() {
                        pTextView.postInvalidate();
                    }
                }), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return stringBuilder;
    }
}