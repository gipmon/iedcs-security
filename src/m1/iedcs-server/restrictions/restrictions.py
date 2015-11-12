
def restriction_country(book, user_data):
    if user_data.country == "PT":
        return False
    elif user_data.country == "NL":
        return False
    else:
        return True


def restriction_cpu_model(book, user_data):
    if user_data.cpu_model == "MacBook Air":
        return False
    else:
        return True

methods = {'restriction_country': restriction_country, 'restriction_cpu_model': restriction_cpu_model}


def test_restriction(function_name, book, user_data):
    try:
        return methods[function_name](book, user_data)
    except Exception, e:
        return False


