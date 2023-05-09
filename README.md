onClickItem trong Adapter.RecyclerView
        holder.cardView.setOnClickListener(view -> iClickItemStudentListener.onClickItemStudent(student));

Room database - model - db - dao

select: item_view - onOptionsItemSelected


hiệu ứng click trong BaseAct //act cha
    @Override
    public final void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_item));
        clickViews(view);
    } // file anim_item bỏ trong res/anim/anim_item.xml
<include toolbar
//tao khoang cach dep hon
Custom item Recycler View
    android:layout_marginStart="7dp"
    android:layout_marginTop="10dp"
    android:layout_marginEnd="7dp"
    app:cardCornerRadius="7dp"

xóa: https://www.youtube.com/watch?v=acoNSVMQwq0 
thêm: https://www.youtube.com/watch?v=qnqPeesEiYg dùng ActitivyResultAPI
sửa: https://www.youtube.com/watch?v=VhlQpXaiFqA&t=1566s  dùng ActitivyResultAPI

sử dụng splashLoading: dùng gif, import thư viện....


search view trong recycler view:
dùng <androidx.appcompat.widget.SearchView
link: https://www.youtube.com/watch?v=tQ7V7iBg5zE&t=61s

import các thư viện, dùng Animatoo: https://github.com/AtifSayings/Animatoo?fbclid=IwAR2Uxqp5i00OnlJWdJuZ0gnHTu4nr1lSeR5UNQ2EnBsoUSbpKjGgwQpros4
