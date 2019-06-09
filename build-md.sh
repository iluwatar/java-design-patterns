#!/usr/bin/env bash

md_file="./java-pattern-all.md"

function handleDir() {
    for element in `ls $1 | sort`
    do
        file=$1"/"$element

        echo $file | grep ".png" >> /dev/null
        if [ $? == 0 ]
        then
            echo $file
            echo "![${file}](${file})" >> "${md_file}"
            echo "" >> "${md_file}"
        fi

        echo $file | grep ".md" >> /dev/null
        if [ $? == 0 ]
        then
            echo $file
            echo "[${file}](${file})" >> "${md_file}"
            echo "" >> "${md_file}"
        fi

        echo $file | grep ".html" >> /dev/null
        if [ $? == 0 ]
        then
            echo $file
            echo "[${file}](${file})" >> "${md_file}"
            echo "" >> "${md_file}"
        fi

        if [ -d $file ]
        then
            echo $file | grep "src" >> /dev/null
            if [ $? == 0 ]
            then
                continue
            fi
            handleDir $file
        fi
    done
}

echo "<!-- vim-markdown-toc GFM -->" > "${md_file}"
echo "" >> "${md_file}"

for dir_item in $(ls ./ | sort) ; do
    [ ! -d $dir_item ] && continue
    echo "* [${dir_item}](#${dir_item})" >> "${md_file}"
done

echo "" >> "${md_file}"
echo "" >> "${md_file}"


for dir_item in $(ls ./ | sort) ; do
    [ ! -d $dir_item ] && continue
    echo "## ${dir_item}" >> "${md_file}"
    handleDir ${dir_item}
    echo "" >> "${md_file}"
    echo "" >> "${md_file}"

done

