/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.model.view.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.iluwatar.model.view.viewmodel.databinding.LayoutBookBinding;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BindingHolder> {

  private List<DesignPatternBook> designPatternBooks;
  private Context context;

  public BookAdapter(List<DesignPatternBook> designPatternBooks, Context context) {
    this.designPatternBooks = designPatternBooks;
    this.context = context;
  }

  @NonNull
  @Override
  public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutBookBinding layoutBookBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()), R.layout.layout_book, parent, false);
    return new BindingHolder(layoutBookBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull BookAdapter.BindingHolder holder, int position) {
    LayoutBookBinding layoutBookBinding = holder.layoutBookBinding;
    layoutBookBinding.setBvm(new BookViewModel(mDesignPatternBooks.get(position), mContext));
  }

  @Override
  public int getItemCount() {
    return mDesignPatternBooks.size();
  }

  public static class BindingHolder extends RecyclerView.ViewHolder {
    private LayoutBookBinding layoutBookBinding;

    public BindingHolder(LayoutBookBinding layoutBookBinding) {
      super(layoutBookBinding.bookCard);
      this.layoutBookBinding = layoutBookBinding;
    }
  }
}
