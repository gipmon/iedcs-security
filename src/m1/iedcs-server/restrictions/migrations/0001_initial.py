# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('books', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='BookRestrictions',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('book', models.ForeignKey(to='books.Book')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Restriction',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('aliasKey', models.CharField(unique=True, max_length=128)),
                ('restrictionFunction', models.CharField(max_length=128)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.AddField(
            model_name='bookrestrictions',
            name='restriction',
            field=models.ForeignKey(to='restrictions.Restriction'),
            preserve_default=True,
        ),
        migrations.AlterUniqueTogether(
            name='bookrestrictions',
            unique_together=set([('book', 'restriction')]),
        ),
    ]
