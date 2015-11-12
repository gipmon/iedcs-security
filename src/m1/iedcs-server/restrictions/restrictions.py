
def restriction_country(book, user_data):
    if user_data.country == "PT":
        return False
    elif user_data.country == "NL":
        return False
    else:
        return True


def restriction_hour(book, user_data):
    if int(user_data.time.split(":")[0]) == 15:
        return False
    else:
        return True

methods = {'restriction_country': restriction_country, 'restriction_hour': restriction_hour}


def test_restriction(function_name, book, user_data):
    try:
        return methods[function_name](book, user_data)
    except Exception, e:
        return False


