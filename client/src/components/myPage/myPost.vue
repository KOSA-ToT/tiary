<template>
    <!-- Container for demo purpose -->
    <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
        <p class="pb-6">
        <div class="container mx-auto my-24 md:px-6">
            <!-- Section: Design Block -->
            <section class="mb-32 text-center md:text-left">
                <h2 class="mb-12 text-lg font-bold text-zinc-600">작성글</h2>
                <div class="flex justify-end">
                    <button @click="deleteSelected"
                        class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">삭제</button>
                    <button @click="selectAll"
                        class="ml-2 rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">전체
                        선택</button>
                </div>
                <div class="flex flex-wrap mb-6" v-for="post in postList" :key="post.id">
                    <!-- <input type="checkbox" :id="'react-option-' + post.id" :checked="allChecked"
                        @change="handleCheckboxChange(post.id)"> -->
                    <div class="w-full px-3 mb-6 ml-auto shrink-0 grow-0 basis-auto md:mb-0 md:w-3/12">
                        <div class="relative mb-6 overflow-hidden bg-no-repeat bg-cover rounded-lg shadow-lg dark:shadow-black/20"
                            data-te-ripple-init data-te-ripple-color="light">
                            <img :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + post.imgUrl" class="w-full"
                                alt="" v-if="post.imgUrl" />
                            <img src="https://mdbcdn.b-cdn.net/img/new/standard/city/018.jpg" class="w-full" alt=""
                                v-else />
                            <a href="#!">
                                <div
                                    class="absolute top-0 right-0 bottom-0 left-0 h-full w-full overflow-hidden bg-fixed opacity-0 transition duration-300 ease-in-out hover:opacity-100 bg-[hsla(0,0%,98.4%,.15)]">
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="w-full px-3 mb-6 mr-auto shrink-0 grow-0 basis-auto md:mb-0 md:w-9/12 xl:w-7/12">
                        <h5 class="mb-3 text-lg font-bold">{{ post.title }}</h5>
                        <div
                            class="flex items-center justify-center mb-3 text-sm font-medium text-danger dark:text-danger-500 md:justify-start">
                            <svg version="1.0" id="Layer_1" xmlns="http://www.w3.org/2000/svg" v-if="post.categoryId == 1"
                                xmlns:xlink="http://www.w3.org/1999/xlink" width="20px" height="20px" viewBox="0 0 64 64"
                                enable-background="new 0 0 64 64" xml:space="preserve" fill="#000000">

                                <g id="SVGRepo_bgCarrier" stroke-width="0" />

                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" />

                                <g id="SVGRepo_iconCarrier">
                                    <g>
                                        <path fill="#394240"
                                            d="M56,46V12c0-0.553-0.447-1-1-1H9c-0.553,0-1,0.447-1,1v34c0,0.553,0.447,1,1,1h46 C55.553,47,56,46.553,56,46z M54,45H10V13h44V45z" />
                                        <path fill="#394240"
                                            d="M63,49h-3V11c0-2.211-1.789-4-4-4H8c-2.211,0-4,1.789-4,4v38H1c-0.553,0-1,0.447-1,1v3c0,2.211,1.789,4,4,4 h56c2.211,0,4-1.789,4-4v-3C64,49.447,63.553,49,63,49z M6,11c0-1.104,0.896-2,2-2h48c1.104,0,2,0.896,2,2v38H36 c-0.553,0-1,0.447-1,1v1h-6v-1c0-0.553-0.447-1-1-1H6V11z M62,53c0,1.104-0.896,2-2,2H4c-1.104,0-2-0.896-2-2v-2h25v1 c0,0.553,0.447,1,1,1h8c0.553,0,1-0.447,1-1v-1h25V53z" />
                                        <g>
                                            <path fill="#F9EBB2"
                                                d="M37,51v1c0,0.553-0.447,1-1,1h-8c-0.553,0-1-0.447-1-1v-1H2v2c0,1.104,0.896,2,2,2h56c1.104,0,2-0.896,2-2 v-2H37z" />
                                            <path fill="#F9EBB2"
                                                d="M29,50v1h6v-1c0-0.553,0.447-1,1-1h22V11c0-1.104-0.896-2-2-2H8c-1.104,0-2,0.896-2,2v38h22 C28.553,49,29,49.447,29,50z M8,46V12c0-0.553,0.447-1,1-1h46c0.553,0,1,0.447,1,1v34c0,0.553-0.447,1-1,1H9 C8.447,47,8,46.553,8,46z" />
                                        </g>
                                        <rect x="10" y="13" fill="#45AAB8" width="44" height="32" />
                                    </g>
                                </g>

                            </svg>
                            <svg version="1.0" id="Layer_1" xmlns="http://www.w3.org/2000/svg" v-if="post.categoryId == 2"
                                xmlns:xlink="http://www.w3.org/1999/xlink" width="20px" height="20px" viewBox="0 0 64 64"
                                enable-background="new 0 0 64 64" xml:space="preserve" fill="#000000">

                                <g id="SVGRepo_bgCarrier" stroke-width="0" />

                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" />

                                <g id="SVGRepo_iconCarrier">
                                    <g>
                                        <g>
                                            <path fill="#506C7F"
                                                d="M15,2C9.477,2,5,6.477,5,12s4.477,10,10,10s10-4.477,10-10S20.523,2,15,2z M10,13c-0.553,0-1-0.447-1-1 s0.447-1,1-1s1,0.447,1,1S10.553,13,10,13z M15,18c-0.553,0-1-0.447-1-1s0.447-1,1-1s1,0.447,1,1S15.553,18,15,18z M15,8 c-0.553,0-1-0.447-1-1s0.447-1,1-1s1,0.447,1,1S15.553,8,15,8z M20,13c-0.553,0-1-0.447-1-1s0.447-1,1-1s1,0.447,1,1 S20.553,13,20,13z" />
                                            <path fill="#506C7F"
                                                d="M37,2c-5.523,0-10,4.477-10,10s4.477,10,10,10s10-4.477,10-10S42.523,2,37,2z M32,13c-0.553,0-1-0.447-1-1 s0.447-1,1-1s1,0.447,1,1S32.553,13,32,13z M37,18c-0.553,0-1-0.447-1-1s0.447-1,1-1s1,0.447,1,1S37.553,18,37,18z M37,8 c-0.553,0-1-0.447-1-1s0.447-1,1-1s1,0.447,1,1S37.553,8,37,8z M42,13c-0.553,0-1-0.447-1-1s0.447-1,1-1s1,0.447,1,1 S42.553,13,42,13z" />
                                        </g>
                                        <path fill="#45AAB8"
                                            d="M50,60c0,1.104-0.896,2-2,2H4c-1.104,0-2-0.896-2-2V26c0-1.104,0.896-2,2-2h44c1.104,0,2,0.896,2,2V60z" />
                                        <g>
                                            <polygon fill="#F9EBB2" points="52,51.279 58,53.279 58,32.721 52,34.72 " />
                                            <polygon fill="#F9EBB2" points="60,32.055 60,53.945 62,54.612 62,31.388 " />
                                        </g>
                                        <path fill="#394240"
                                            d="M39,12c0,1.656,1.344,3,3,3s3-1.344,3-3s-1.344-3-3-3S39,10.344,39,12z M42,11c0.553,0,1,0.447,1,1 s-0.447,1-1,1s-1-0.447-1-1S41.447,11,42,11z" />
                                        <path fill="#394240"
                                            d="M37,10c1.656,0,3-1.344,3-3s-1.344-3-3-3s-3,1.344-3,3S35.344,10,37,10z M37,6c0.553,0,1,0.447,1,1 s-0.447,1-1,1s-1-0.447-1-1S36.447,6,37,6z" />
                                        <path fill="#394240"
                                            d="M37,14c-1.656,0-3,1.344-3,3s1.344,3,3,3s3-1.344,3-3S38.656,14,37,14z M37,18c-0.553,0-1-0.447-1-1 s0.447-1,1-1s1,0.447,1,1S37.553,18,37,18z" />
                                        <path fill="#394240"
                                            d="M35,12c0-1.656-1.344-3-3-3s-3,1.344-3,3s1.344,3,3,3S35,13.656,35,12z M31,12c0-0.553,0.447-1,1-1 s1,0.447,1,1s-0.447,1-1,1S31,12.553,31,12z" />
                                        <path fill="#394240"
                                            d="M23,12c0-1.656-1.344-3-3-3s-3,1.344-3,3s1.344,3,3,3S23,13.656,23,12z M20,13c-0.553,0-1-0.447-1-1 s0.447-1,1-1s1,0.447,1,1S20.553,13,20,13z" />
                                        <path fill="#394240"
                                            d="M15,10c1.656,0,3-1.344,3-3s-1.344-3-3-3s-3,1.344-3,3S13.344,10,15,10z M15,6c0.553,0,1,0.447,1,1 s-0.447,1-1,1s-1-0.447-1-1S14.447,6,15,6z" />
                                        <path fill="#394240"
                                            d="M15,14c-1.656,0-3,1.344-3,3s1.344,3,3,3s3-1.344,3-3S16.656,14,15,14z M15,18c-0.553,0-1-0.447-1-1 s0.447-1,1-1s1,0.447,1,1S15.553,18,15,18z" />
                                        <path fill="#394240"
                                            d="M13,12c0-1.656-1.344-3-3-3s-3,1.344-3,3s1.344,3,3,3S13,13.656,13,12z M10,13c-0.553,0-1-0.447-1-1 s0.447-1,1-1s1,0.447,1,1S10.553,13,10,13z" />
                                        <g>
                                            <path fill="#394240"
                                                d="M63.585,29.188c-0.262-0.188-0.598-0.241-0.901-0.137L52,32.612V26c0-2.211-1.789-4-4-4h-4.372 C46.864,19.852,49,16.177,49,12c0-6.629-5.374-12-12-12c-4.923,0-9.149,2.968-11,7.211C24.149,2.968,19.923,0,15,0 C8.374,0,3,5.371,3,12c0,4.177,2.136,7.852,5.372,10H4c-2.211,0-4,1.789-4,4v34c0,2.211,1.789,4,4,4h44c2.211,0,4-1.789,4-4 v-6.613l10.684,3.562C62.787,56.983,62.894,57,63,57c0.207,0,0.412-0.064,0.585-0.188C63.846,56.623,64,56.321,64,56V30 C64,29.679,63.846,29.377,63.585,29.188z M37,2c5.523,0,10,4.477,10,10s-4.477,10-10,10s-10-4.477-10-10S31.477,2,37,2z M30.372,22h-8.744c1.916-1.272,3.443-3.08,4.372-5.211C26.929,18.92,28.456,20.728,30.372,22z M5,12C5,6.477,9.477,2,15,2 s10,4.477,10,10s-4.477,10-10,10S5,17.523,5,12z M50,60c0,1.104-0.896,2-2,2H4c-1.104,0-2-0.896-2-2V26c0-1.104,0.896-2,2-2h44 c1.104,0,2,0.896,2,2V60z M58,53.279l-6-2V34.72l6-1.999V53.279z M62,54.612l-2-0.667V32.055l2-0.667V54.612z" />
                                            <path fill="#394240"
                                                d="M36,36H16c-0.553,0-1,0.448-1,1v12c0,0.552,0.447,1,1,1h20c0.553,0,1-0.448,1-1V37 C37,36.448,36.553,36,36,36z M35,48H17V38h18V48z" />
                                        </g>
                                        <polygon opacity="0.15" points="62,54.612 60,53.945 60,32.055 62,31.388 " />
                                        <rect x="17" y="38" opacity="0.2" fill="#FFFFFF" width="18" height="10" />
                                    </g>
                                </g>

                            </svg>
                            <svg version="1.0" id="Layer_1" xmlns="http://www.w3.org/2000/svg" v-if="post.categoryId == 3"
                                xmlns:xlink="http://www.w3.org/1999/xlink" width="20px" height="20px" viewBox="0 0 64 64"
                                enable-background="new 0 0 64 64" xml:space="preserve" fill="#000000">

                                <g id="SVGRepo_bgCarrier" stroke-width="0" />

                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" />

                                <g id="SVGRepo_iconCarrier">
                                    <g>
                                        <path fill="#F76D57"
                                            d="M58.714,29.977c0,0-0.612,0.75-1.823,1.961S33.414,55.414,33.414,55.414C33.023,55.805,32.512,56,32,56 s-1.023-0.195-1.414-0.586c0,0-22.266-22.266-23.477-23.477s-1.823-1.961-1.823-1.961C3.245,27.545,2,24.424,2,21 C2,13.268,8.268,7,16,7c3.866,0,7.366,1.566,9.899,4.101l0.009-0.009l4.678,4.677c0.781,0.781,2.047,0.781,2.828,0l4.678-4.677 l0.009,0.009C40.634,8.566,44.134,7,48,7c7.732,0,14,6.268,14,14C62,24.424,60.755,27.545,58.714,29.977z" />
                                        <path fill="#F76D57"
                                            d="M58.714,29.977c0,0-0.612,0.75-1.823,1.961S33.414,55.414,33.414,55.414C33.023,55.805,32.512,56,32,56 s-1.023-0.195-1.414-0.586c0,0-22.266-22.266-23.477-23.477s-1.823-1.961-1.823-1.961C3.245,27.545,2,24.424,2,21 C2,13.268,8.268,7,16,7c3.866,0,7.366,1.566,9.899,4.101l0.009-0.009l4.678,4.677c0.781,0.781,2.047,0.781,2.828,0l4.678-4.677 l0.009,0.009C40.634,8.566,44.134,7,48,7c7.732,0,14,6.268,14,14C62,24.424,60.755,27.545,58.714,29.977z" />
                                        <g>
                                            <path fill="#394240"
                                                d="M48,5c-4.418,0-8.418,1.791-11.313,4.687l-3.979,3.961c-0.391,0.391-1.023,0.391-1.414,0 c0,0-3.971-3.97-3.979-3.961C24.418,6.791,20.418,5,16,5C7.163,5,0,12.163,0,21c0,3.338,1.024,6.436,2.773,9 c0,0,0.734,1.164,1.602,2.031s24.797,24.797,24.797,24.797C29.953,57.609,30.977,58,32,58s2.047-0.391,2.828-1.172 c0,0,23.93-23.93,24.797-24.797S61.227,30,61.227,30C62.976,27.436,64,24.338,64,21C64,12.163,56.837,5,48,5z M58.714,29.977 c0,0-0.612,0.75-1.823,1.961S33.414,55.414,33.414,55.414C33.023,55.805,32.512,56,32,56s-1.023-0.195-1.414-0.586 c0,0-22.266-22.266-23.477-23.477s-1.823-1.961-1.823-1.961C3.245,27.545,2,24.424,2,21C2,13.268,8.268,7,16,7 c3.866,0,7.366,1.566,9.899,4.101l0.009-0.009l4.678,4.677c0.781,0.781,2.047,0.781,2.828,0l4.678-4.677l0.009,0.009 C40.634,8.566,44.134,7,48,7c7.732,0,14,6.268,14,14C62,24.424,60.755,27.545,58.714,29.977z" />
                                            <path fill="#394240"
                                                d="M48,11c-0.553,0-1,0.447-1,1s0.447,1,1,1c4.418,0,8,3.582,8,8c0,0.553,0.447,1,1,1s1-0.447,1-1 C58,15.478,53.522,11,48,11z" />
                                        </g>
                                    </g>
                                </g>

                            </svg>
                            <svg version="1.0" id="Layer_1" xmlns="http://www.w3.org/2000/svg" v-if="post.categoryId == 4"
                                xmlns:xlink="http://www.w3.org/1999/xlink" width="20px" height="20px" viewBox="0 0 64 64"
                                enable-background="new 0 0 64 64" xml:space="preserve" fill="#000000">

                                <g id="SVGRepo_bgCarrier" stroke-width="0" />

                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" />

                                <g id="SVGRepo_iconCarrier">
                                    <g>
                                        <g>
                                            <path fill="#F9EBB2"
                                                d="M62,60c0,1.104-0.896,2-2,2H4c-1.104,0-2-0.896-2-2V4c0-1.104,0.896-2,2-2h56c1.104,0,2,0.896,2,2V60z" />
                                            <g>
                                                <path fill="#394240"
                                                    d="M60,0H4C1.789,0,0,1.789,0,4v56c0,2.211,1.789,4,4,4h56c2.211,0,4-1.789,4-4V4C64,1.789,62.211,0,60,0z M62,60c0,1.104-0.896,2-2,2H4c-1.104,0-2-0.896-2-2V4c0-1.104,0.896-2,2-2h56c1.104,0,2,0.896,2,2V60z" />
                                                <path fill="#394240"
                                                    d="M35,8h-6c-0.553,0-1,0.447-1,1v47h8V9C36,8.447,35.553,8,35,8z M34,54h-4V40h4V54z M34,38h-4V10h4V38z" />
                                                <path fill="#394240"
                                                    d="M25,16h-6c-0.553,0-1,0.447-1,1v39h8V17C26,16.447,25.553,16,25,16z M24,54h-4V44h4V54z M24,42h-4V18h4 V42z" />
                                                <path fill="#394240"
                                                    d="M55,24h-6c-0.553,0-1,0.447-1,1v31h8V25C56,24.447,55.553,24,55,24z M54,54h-4V44h4V54z M54,42h-4V26h4 V42z" />
                                                <path fill="#394240"
                                                    d="M45,32h-6c-0.553,0-1,0.447-1,1v23h8V33C46,32.447,45.553,32,45,32z M44,54h-4v-6h4V54z M44,46h-4V34h4 V46z" />
                                                <path fill="#394240"
                                                    d="M15,24H9c-0.553,0-1,0.447-1,1v31h8V25C16,24.447,15.553,24,15,24z M14,54h-4v-6h4V54z M14,46h-4V26h4V46 z" />
                                            </g>
                                        </g>
                                        <g>
                                            <rect x="10" y="26" fill="#B4CCB9" width="4" height="20" />
                                            <rect x="20" y="18" fill="#45AAB8" width="4" height="24" />
                                            <rect x="40" y="34" fill="#F76D57" width="4" height="12" />
                                            <rect x="30" y="40" fill="#45AAB8" width="4" height="14" />
                                            <rect x="50" y="26" fill="#B4CCB9" width="4" height="16" />
                                            <rect x="30" y="10" fill="#45AAB8" width="4" height="28" />
                                            <rect x="10" y="48" fill="#B4CCB9" width="4" height="6" />
                                            <rect x="40" y="48" fill="#F76D57" width="4" height="6" />
                                            <rect x="50" y="44" fill="#B4CCB9" width="4" height="10" />
                                            <rect x="20" y="44" fill="#45AAB8" width="4" height="10" />
                                        </g>
                                        <g opacity="0.2">
                                            <rect x="30" y="40" width="4" height="14" />
                                            <rect x="10" y="48" width="4" height="6" />
                                            <rect x="40" y="48" width="4" height="6" />
                                            <rect x="50" y="44" width="4" height="10" />
                                            <rect x="20" y="44" width="4" height="10" />
                                        </g>
                                    </g>
                                </g>

                            </svg>
                            {{ post.categoryName }}
                        </div>
                        <p class="mb-6 text-neutral-500 dark:text-neutral-300">
                            <small>작성일시 : {{ formatCreatedAt(post.createdAt) }}</small>
                        </p>

                    </div>
                    <input type="checkbox" :id="'react-option-' + post.id" :checked="allChecked"
                        @change="handleCheckboxChange(post.id)">
                </div>
            </section>

            <!-- Section: Design Block -->
            <section v-if="totalPages != 0"><!-- 페이징 -->
                <div class="flex items-center justify-between px-4 py-3 sm:px-6 bg-back">
                    <div class="flex justify-between flex-1 sm:hidden">
                        <a
                            class="relative inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50">Previous</a>
                        <a
                            class="relative inline-flex items-center px-4 py-2 ml-3 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50">Next</a>
                    </div>
                    <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-center">
                        <div>
                            <nav class="inline-flex -space-x-px rounded-md shadow-sm isolate" aria-label="Pagination">
                                <a @click="prevPage" :disabled="currentPage === 1"
                                    class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-l-md ring-1 ring-inset ring-gray-300 hover:bg-commu-pagination-hover focus:z-20 focus:outline-offset-0"
                                    id="previousPage">
                                    <span class="sr-only">Previous</span>
                                    <svg class="w-5 h-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                        <path fill-rule="evenodd"
                                            d="M12.79 5.23a.75.75 0 01-.02 1.06L8.832 10l3.938 3.71a.75.75 0 11-1.04 1.08l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 011.06.02z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </a>
                                <a v-for="page in visiblePages" :key="page" @click="clickPage(page - 1)"
                                    :aria-current="currentPage === page - 1 ? 'page' : null"
                                    :class="{ 'hover:bg-commu-pagination-hover': totalPages > page - 1 }"
                                    class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 duration-200 ease-in-out ring-1 ring-inset ring-gray-300 focus:z-20 focus:outline-offset-0 page-link">
                                    {{ page }}
                                </a><a @click="nextPage" :disabled="currentPage === totalPages"
                                    class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-r-md ring-1 ring-inset ring-gray-300 hover:bg-commu-pagination-hover focus:z-20 focus:outline-offset-0"
                                    id="nextPage">
                                    <span class="sr-only">Next</span>
                                    <svg class="w-5 h-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                        <path fill-rule="evenodd"
                                            d="M7.21 14.77a.75.75 0 01.02-1.06L11.168 10 7.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </a>
                            </nav>
                        </div>
                    </div>
                </div>
            </section>
            <p v-else>게시물이 없습니다.</p>
        </div>
        </p>
    </div>
    <!-- Container for demo purpose -->
</template>
<script setup>
import { defineProps, onMounted, ref, watchEffect, watch } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { listMyPost } from '@/api/common';
const postList = ref([]);
const allChecked = ref(false);
const totalPages = ref(0);
const currentPage = ref(0);
const visiblePages = ref([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
const pageCalculate = ref();

const props = defineProps({
    user: {
        type: Object,
        required: true,
    },
});
const Post = {
    title: ref(),
    content: ref()
}

async function listMyPosts() {
    try {
        pageCalculate.value = Math.floor((currentPage.value) / 10);
        console.log(pageCalculate.value);
        pageCalculate.value = (pageCalculate.value) * 10;
        visiblePages.value = Array.from({ length: 10 }, (_, index) => pageCalculate.value + index + 1);
        const response = await listMyPost(props.user.id.value, currentPage.value);
        postList.value = response.data.content;
        totalPages.value = response.data.totalPages;
        console.log("response.data : " + response.data.content);
        console.log("response.data.totalPages : " + response.data.totalPages);
    } catch (error) {
        console.log(error);
    }
}
function prevPage() {
    if (currentPage.value > 0) {
        currentPage.value -= 1;
        listMyPosts();
    }
}
function nextPage() {
    if (currentPage.value < totalPages.value - 1) {
        currentPage.value += 1;
        listMyPosts();
    }
}
function clickPage(num) {
    if (num < totalPages.value) {
        currentPage.value = num;
        listMyPosts();
    }
}
const formatCreatedAt = (createdAt) => {
    // createdAt이 문자열인 경우 Date 객체로 변환
    const date = typeof createdAt === 'string' ? new Date(createdAt) : createdAt;

    // Date 객체를 원하는 형식으로 변환
    const formattedDate = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ` +
        `${date.getHours()}시 ${date.getMinutes()}분 ${date.getSeconds()}초`;

    return formattedDate;
};
const deleteSelected = () => {
    // 선택한 게시물의 ID를 서버에 전송
    axios.delete('/{article-id}/{comment-id}', { selectedPosts: selectedPosts.value })
        .then((response) => {
            console.log('삭제 요청이 성공했습니다.');
            // 성공적으로 서버에서 응답을 받으면 선택한 게시물 목록에서 제거
            postList.value = postList.value.filter(post => !selectedPosts.value.includes(post.id));
            // 선택한 게시물 배열 초기화
            selectedPosts.value = [];
        })
        .catch((error) => {
            console.error('삭제 요청 중 오류가 발생했습니다.', error);
        });
}; // 나중에 수정..

const selectAll = () => {
    console.log("선택됨");
    allChecked.value = !allChecked.value;
    // 전체 선택 상태에 따라 postList의 각 항목의 선택 상태를 업데이트
    postList.value.forEach(post => (post.selected = allChecked.value));
};
const handleCheckboxChange = () => {
    // 개별 체크박스 상태 변경에 대한 로직 추가
    // postId를 이용하여 해당 포스트의 선택 여부를 처리
};
onMounted(() => {
    listMyPosts();
});
watch(() => props.user.id.value, () => {
    listMyPosts();
});
</script>
<style>
.page-link[aria-total="page"] {
    background-color: #805959;
    /* 현재 페이지 버튼의 배경색을 변경 */
}

/* 현재 페이지 버튼의 스타일 */
.page-link[aria-current="page"] {
    background-color: #ffc5f8;
    /* 현재 페이지 버튼의 배경색을 변경 */
    color: #FFFFFF;
    /* 현재 페이지 버튼의 텍스트 색상을 변경 */
}
</style>