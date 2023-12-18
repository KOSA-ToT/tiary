<template>
  <div class="relative bg-stone-50 px-6 pb-15 lg:px-8 lg:pb-16 dark:bg-gray-50">
  <!-- ?? -->
  <!-- <div class="absolute inset-0">
    <div class="h-1/3 bg-white sm:h-2/3"></div>
  </div> -->

  <div class="relative mx-auto max-w-7xl">
    <!-- Title -->
        <!-- 이미지를 가운데 정렬하도록 클래스 추가 -->
  <div class="text-center">
    <img style="display: block; margin: 0 auto;" src="/images/editor_choice.png" alt="Random Image">
  </div>

    <!-- 카드 묶음 -->
    <div class="mx-auto mt-12 grid max-w-lg gap-5 lg:max-w-none lg:grid-cols-3">
      <!-- 카드 1-->
      <div class="flex flex-col overflow-hidden rounded-sm shadow-md hover:shadow-orange-300">
        <div class="flex-shrink-0">
          <img class="object-cover h-64 w-full" src="/images/logo.png" alt="">
        </div>
        <div class="flex flex-1 flex-col justify-between bg-white p-6">
          <div class="flex-1">
            <p class="text-sm font-medium text-indigo-600">
              <a href="#" class="hover:underline">Article</a>
            </p>
            <a href="#" class="mt-2 block">
              <p class="text-xl font-semibold text-gray-900">Boost your conversion rate</p>
              <p class="mt-3 text-base text-gray-500">Lorem ipsum dolor sit amet consectetur adipisicing elit.
                Architecto accusantium praesentium eius, ut atque fuga culpa, similique sequi cum eos quis dolorum.</p>
            </a>
          </div>
          <!-- 해시태그 -->
          <ul class="mt-4 flex flex-wrap items-center justify-start text-sm gap-2">
                <li title="Pricing type"
                    class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                    <span>여행/문화</span>
                </li>
                <li title="Support for API"
                    class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                    <span>API</span>
                </li>
            </ul>
        </div>
      </div>

      <!-- 카드 2 -->
      <div class="flex flex-col overflow-hidden shadow-md hover:shadow-orange-300">
        <a href="/">
          <div class="flex-shrink-0">
            <img class="object-cover h-64 w-full" src="/images/dog.jpg" alt="">
          </div>
          <div class="flex flex-1 flex-col bg-white p-6">
            <div class="flex-1">
              <div class="flex flex-col justify-center items-center">
                <p class="text-sm font-medium text-gray-400">일상 / 취미</p>
                <hr class="gray-300 my-2 w-3/4">
                <p class="text-xl font-semibold text-gray-900">아쿠아리움 갔다왔어요!</p>
                <p class="mt-3 text-base text-gray-500">요즘 핫하다는 아쿠아리움 갔다왔어요 ㅎ ㅎ 우리나라에 있는 아쿠아리움 중에 가장 큰 아쿠아리움이라고 해요! 우리 애도 어찌나 좋아하던지 방방 뛰어다니더라구요 ㅎㅎ 말리느라 고생했어요</p>
              </div>
            </div>
            <!-- 해시태그 -->
            <ul class="mt-4 flex flex-wrap items-center justify-start text-sm gap-2">
                  <li title="Pricing type"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>여행/문화</span>
                  </li>
                  <li title="Support for API"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>API</span>
                  </li>
              </ul>
            </div>
        </a>
      </div>

      <!-- 카드 3 -->
      <div class="flex flex-col overflow-hidden rounded-lg shadow-lg">
        <div class="flex-shrink-0">
          <img class="h-48 w-full object-cover" src="https://images.unsplash.com/photo-1492724441997-5dc865305da7?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=1679&amp;q=80" alt="">
        </div>
        <div class="flex flex-1 flex-col justify-between bg-white p-6">
          <div class="flex-1">
            <p class="text-sm font-medium text-indigo-600">
              <a href="#" class="hover:underline">Case Study</a>
            </p>
            <a href="#" class="mt-2 block">
              <p class="text-xl font-semibold text-gray-900">Improve your customer experience</p>
              <p class="mt-3 text-base text-gray-500">Lorem ipsum dolor sit amet consectetur adipisicing elit. Sint
                harum rerum voluptatem quo recusandae magni placeat saepe molestiae, sed excepturi cumque corporis
                perferendis hic.</p>
            </a>
          </div>
          <div class="mt-6 flex items-center">
            <div class="flex-shrink-0">
              <a href="#">
                <span class="sr-only">Daniela Metz</span>
                <img class="h-10 w-10 rounded-full" src="https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=facearea&amp;facepad=2&amp;w=256&amp;h=256&amp;q=80" alt="">
              </a>
            </div>
            <div class="ml-3">
              <p class="text-sm font-medium text-gray-900">
                <a href="#" class="hover:underline">Daniela Metz</a>
              </p>
              <div class="flex space-x-1 text-sm text-gray-500">
                <time datetime="2020-02-12">Feb 12, 2020</time>
                <span aria-hidden="true">·</span>
                <span>11 min read</span>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>

</template>