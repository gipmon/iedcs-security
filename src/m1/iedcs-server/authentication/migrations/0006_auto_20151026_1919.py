# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0005_auto_20151026_1915'),
    ]

    operations = [
        migrations.AlterField(
            model_name='usercollecteddata',
            name='country',
            field=models.CharField(default=b' ', max_length=128),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='usercollecteddata',
            name='cpu_model',
            field=models.CharField(default=b' ', max_length=128),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='usercollecteddata',
            name='ip',
            field=models.CharField(default=b' ', max_length=128),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='usercollecteddata',
            name='op_system',
            field=models.CharField(default=b' ', max_length=128),
            preserve_default=True,
        ),
        migrations.AlterField(
            model_name='usercollecteddata',
            name='timezone',
            field=models.CharField(default=b' ', max_length=128),
            preserve_default=True,
        ),
    ]
